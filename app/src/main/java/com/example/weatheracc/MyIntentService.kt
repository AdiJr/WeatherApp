package com.example.weatheracc

import android.app.IntentService
import android.content.Intent
import android.widget.Toast

class MyIntentService : IntentService("MyIntentService") {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "Service starting", Toast.LENGTH_LONG).show()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onHandleIntent(intent: Intent?) {
        Toast.makeText(this, "Hej, to ja - Serwis", Toast.LENGTH_LONG).show()
    }
}