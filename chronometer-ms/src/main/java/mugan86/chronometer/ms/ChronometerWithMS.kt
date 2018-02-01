package mugan86.chronometer.ms

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Message
import android.os.SystemClock
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import java.text.DecimalFormat


/********************************************************************************
 * Created by Anartz Mugika (mugan86@gmail.com) on 31/1/18.
 ********************************************************************************/
class ChronometerWithMS @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : TextView(context, attrs, defStyle) {

    private var mBase: Long = 0
    private var mVisible: Boolean = false
    private var mStarted: Boolean = false
    var isRunning: Boolean = false
        private set
    private var text: String? = null
    var onChronometerTickListener: OnChronometerTickListener? = null

    private var timeElapsed: Long = 0

    var base: Long
        get() = mBase
        set(base) {
            mBase = base
            dispatchChronometerTick()
            updateText(SystemClock.elapsedRealtime())
        }

    private val mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(m: Message) {
            if (isRunning) {
                updateText(now = SystemClock.elapsedRealtime())
                dispatchChronometerTick()
                sendMessageDelayed(Message.obtain(this, TICK_WHAT),
                        100)
            }
        }
    }

    interface OnChronometerTickListener {

        fun onChronometerTick(chronometer: ChronometerWithMS)
    }

    init {

        init()
    }

    private fun init() {
        mBase = SystemClock.elapsedRealtime()
        updateText(mBase)
    }

    fun start() {
        mBase = SystemClock.elapsedRealtime()
        mStarted = true
        updateRunning()
    }

    fun stop() {
        mStarted = false
        updateRunning()
    }

    fun resetTime() {
        init()
    }

    fun restart() {
        mStarted = true
        mBase = SystemClock.elapsedRealtime() - timeElapsed
        updateRunning()
    }


    fun setStarted(started: Boolean) {
        mStarted = started
        updateRunning()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mVisible = false
        updateRunning()
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        mVisible = visibility == View.VISIBLE
        updateRunning()
    }

    @Synchronized
    private fun updateText(now: Long) {
        timeElapsed = now - mBase

        val df = DecimalFormat("00")

        val hours = (timeElapsed / (3600 * 1000)).toInt()
        var remaining = (timeElapsed % (3600 * 1000)).toInt()

        val minutes = remaining / (60 * 1000)
        remaining = remaining % (60 * 1000)

        val seconds = remaining / 1000
        remaining = remaining % 1000

        val milliseconds = timeElapsed.toInt() % 1000 / 100

        text = ""

        if (hours > 0) {
            text += df.format(hours) + ":"
        }

        text += df.format(minutes) + ":"
        text += df.format(seconds) + ":"
        text += Integer.toString(milliseconds)

        setText(text)

    }

    private fun updateRunning() {
        val running = mVisible && mStarted
        if (running != isRunning) {
            if (running) {
                updateText(SystemClock.elapsedRealtime())
                dispatchChronometerTick()
                mHandler.sendMessageDelayed(Message.obtain(mHandler,
                        TICK_WHAT), 100)
            } else {
                mHandler.removeMessages(TICK_WHAT)
            }
            isRunning = running
        }
    }

    internal fun dispatchChronometerTick() {
        if (onChronometerTickListener != null) {
            onChronometerTickListener!!.onChronometerTick(this@ChronometerWithMS)
        }
    }

    companion object {

        private const val TICK_WHAT = 2
    }

}