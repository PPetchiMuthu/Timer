package com.example.android.timer

import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var stopWatch: Chronometer
    private var running = false
    private var offset: Long = 0

    private val OFFSET_KEY = "offset"
    private val RUNNING_KEY = "running"
    private val BASE_KEY = "base"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        stopWatch = findViewById(R.id.Timer)

        //restore the value
        if(savedInstanceState!=null){
            offset = savedInstanceState.getLong(OFFSET_KEY)
            running = savedInstanceState.getBoolean(RUNNING_KEY)
            if (running){
                stopWatch.base = savedInstanceState.getLong(BASE_KEY)
                stopWatch.start()
            }else setBaseTime()
        }

        val startButton = findViewById<Button>(R.id.start)
        startButton.setOnClickListener {
            if (!running) {
                setBaseTime()
                stopWatch.start()
                running = true
            }
        }
        val pauseButton = findViewById<Button>(R.id.pause)
        pauseButton.setOnClickListener {
            if (running) {
                saveOffset()
                stopWatch.stop()
                running = false
            }
        }
        val resetButton = findViewById<Button>(R.id.reset)
        resetButton.setOnClickListener {
            offset = 0
            setBaseTime()
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putLong(OFFSET_KEY,offset)
        savedInstanceState.putBoolean(RUNNING_KEY,running)
        savedInstanceState.putLong(BASE_KEY,stopWatch.base)
    }

    private fun setBaseTime() {
        stopWatch.base = SystemClock.elapsedRealtime() - offset
    }

    private fun saveOffset() {
        offset = SystemClock.elapsedRealtime() - stopWatch.base
    }
}