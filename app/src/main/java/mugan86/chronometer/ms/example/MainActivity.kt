package mugan86.chronometer.ms.example

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        start_pause_chronoButton!!.text = "Start"
        stop_reset_chronoButton!!.visibility = View.GONE

        start_pause_chronoButton!!.setOnClickListener {
            if (start_pause_chronoButton!!.text.toString().equals("Start")) {
                if (chronometer!!.isRunning)
                    chronometer!!.start()
                else
                    chronometer!!.restart()
                start_pause_chronoButton!!.text = "Pause"
                stop_reset_chronoButton!!.visibility = View.VISIBLE
                stop_reset_chronoButton!!.text = "Reset"
            } else {
                start_pause_chronoButton!!.text = "Start"
                chronometer!!.stop()
                stop_reset_chronoButton!!.visibility = View.VISIBLE
            }
        }

        stop_reset_chronoButton!!.setOnClickListener {
            chronometer!!.stop()
            chronometer!!.start()
            stop_reset_chronoButton!!.text = "Reset"
            start_pause_chronoButton!!.text = "Pause"
            stop_reset_chronoButton!!.visibility = View.GONE
        }

    }
}