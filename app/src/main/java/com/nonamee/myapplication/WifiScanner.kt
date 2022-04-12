package com.nonamee.myapplication

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Toast
import java.io.File


class WifiScanner : Service() {

    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable

    val wifimanager = getSystemService(Context.WIFI_SERVICE) as WifiManager



    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        toast("я родился")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        toast("Raining man")
        mHandler = Handler(Looper.getMainLooper())
        mRunnable = storeData()
        mHandler.postDelayed(mRunnable,3000)

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacks(mRunnable)
    }


    private fun storeData(): Runnable {
        wifimanager.startScan()
        val networks:List<ScanResult> = wifimanager.scanResults
        File("ScannerLog.json").appendText("{ \"Timestamp\": "+networks[0].timestamp+", \"Data\": [")
        for (point in networks) {
            File("ScannerLog.json").appendText("{\"SSID\":\""+point.SSID+"\", BSSID\":\""+point.BSSID+
                    "\", BSSID\":\""+point.level+"}, ")
        }
        File("ScannerLog.json").appendText("] },")
        toast("Readed"+networks[0].timestamp.toString())

        TODO("everything")
    }

    private fun Context.toast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }


}
