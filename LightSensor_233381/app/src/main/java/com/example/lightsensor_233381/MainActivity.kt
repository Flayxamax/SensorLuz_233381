package com.example.lightsensor_233381

import android.content.ContentValues.TAG
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import java.io.IOException


class MainActivity : AppCompatActivity(), SensorEventListener {

    private var mediaPlayer: MediaPlayer? = null
    var sensor: Sensor? = null
    var sensorManager: SensorManager? = null
    lateinit var image: ImageView
    lateinit var background: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        mediaPlayer = MediaPlayer.create(this, R.raw.sound1)
        image = findViewById(R.id.imageV)
        background = findViewById(R.id.back)
        image.visibility = View.INVISIBLE

        //Inicialización del sensor de luz
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    fun playSound1() {
        mediaPlayer?.start()
    }

    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        try {
            if (event != null) {
                Log.d(TAG, "onSensorChanged:" + event.values[0])
            }

            if (event!!.values[0] < 30) {
                image.visibility = View.INVISIBLE
                background.setBackgroundColor(resources.getColor(R.color.black))
            } else {
                image.visibility = View.VISIBLE
                background.setBackgroundColor(resources.getColor(R.color.white))
                playSound1()
            }
        } catch (e: IOException) {
            Log.d(TAG, "onSensorChanged: +${e.message}")
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }
}