package mugan86.chronometer.ms.example

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import mugan86.chronometer.ms.ChronometerWithMS


class MainActivity : AppCompatActivity() {

    private var chronometer: ChronometerWithMS? = null
    private var start_pause_chronoButton: Button? = null
    private var stop_reset_chronoButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        chronometer = findViewById<View>(R.id.chronometer) as ChronometerWithMS
        start_pause_chronoButton = findViewById(R.id.start_pause_chronoButton) as Button
        stop_reset_chronoButton = findViewById(R.id.stop_reset_chronoButton) as Button
        start_pause_chronoButton!!.text="Start"
        stop_reset_chronoButton!!.visibility=View.GONE

        start_pause_chronoButton!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                if (start_pause_chronoButton!!.text.toString().equals("Start")) {
                    if (chronometer!!.isRunning)
                        chronometer!!.start()
                    else
                        chronometer!!.restart()
                    start_pause_chronoButton!!.text="Pause"
                    stop_reset_chronoButton!!.visibility = View.VISIBLE
                    stop_reset_chronoButton!!.text="Start from 0"
                } else {
                    start_pause_chronoButton!!.text="Start"
                    chronometer!!.stop()
                    stop_reset_chronoButton!!.visibility = View.VISIBLE
                }
            }
        })

        stop_reset_chronoButton!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                chronometer!!.stop()
                chronometer!!.start()
                stop_reset_chronoButton!!.text = "Start from 0"
                start_pause_chronoButton!!.text="Pause"
                stop_reset_chronoButton!!.visibility = View.GONE
            }
        })

    }
}