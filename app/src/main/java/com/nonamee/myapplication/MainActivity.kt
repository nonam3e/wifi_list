package com.nonamee.myapplication

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val serviceClass = WifiScanner::class.java

        val startServiceButton: Button = findViewById(R.id.button)
        startServiceButton.text = "Tap to start"
        var isStarted = false

        startServiceButton.setOnClickListener {
            isStarted = !isStarted
            if (isStarted) {
                // If the service is not running then start it
                if (!isServiceRunning(serviceClass)) {
                    startService(Intent(this, WifiScanner::class.java))
                    toast("trying to start")
                } else {
                    toast("Service already running.")
                }
                startServiceButton.text = "Tap to stop"
            }
            else {
                if (isServiceRunning(serviceClass)) {
                    stopService(intent)
                } else {
                    toast("Service already stopped.")
                }
                startServiceButton.text = "Tap to start"
            }
        }
    }
    private fun Context.toast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun isServiceRunning(serviceClass: Class<*>): Boolean {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        // Loop through the running services
        activityManager.getRunningServices(Integer.MAX_VALUE).forEach { service ->
            if (serviceClass.name == service.service.className) {
                // If the service is running then return true
                return true
            }
        }
        return false
    }
}