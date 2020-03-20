package com.example.weatheracc

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TargetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_target)

        val textView: TextView = findViewById(R.id.textView)
        val receivedText = intent.getStringExtra("text")
        textView.text = receivedText
    }
}