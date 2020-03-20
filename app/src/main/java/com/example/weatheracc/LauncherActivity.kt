package com.example.weatheracc

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LauncherActivity : AppCompatActivity() {
    private var mBluetoothAdapter: BluetoothAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        val btButton: Button = findViewById(R.id.btButton)
        val serviceButton: Button = findViewById(R.id.startServiceButton)
        val intentButton: Button = findViewById(R.id.newActivityButton)
        val editText: EditText = findViewById(R.id.editText)
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        btButton.setOnClickListener { startConnection() }

        intentButton.setOnClickListener {
            val intent = Intent(this, TargetActivity::class.java).apply {
                //text from user to be passed to next activity and displayed there in TextView
                putExtra("text", editText.text.toString())
            }
            startActivity(intent)
        }

        serviceButton.setOnClickListener {
            val intent = Intent(this, MyIntentService::class.java).also {
                startService(intent)
            }
        }
    }

    override fun onPause() {
        this.unregisterReceiver(mBluetoothStateReceiver)
        super.onPause()
    }

    override fun onResume() {
        val btIntent = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
        this.registerReceiver(mBluetoothStateReceiver, btIntent)
        super.onResume()
    }

    // Receiver for listening to Bluetooth state changes
    private val mBluetoothStateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action!!
            if (action == BluetoothAdapter.ACTION_STATE_CHANGED) {
                when (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)) {
                    BluetoothAdapter.STATE_OFF -> {
                        Toast.makeText(applicationContext, "Bluetooth OFF", Toast.LENGTH_LONG)
                            .show()
                    }
                    BluetoothAdapter.STATE_TURNING_OFF, BluetoothAdapter.STATE_TURNING_ON -> {
                    }
                    BluetoothAdapter.STATE_ON -> {
                        Toast.makeText(applicationContext, "Bluetooth ON", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun startConnection() {
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "No Bluetooth", Toast.LENGTH_SHORT).show()
        } else {
            if (!mBluetoothAdapter!!.isEnabled) {
                val enableBluetooth = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivity(enableBluetooth)
                val btIntent = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
                this.registerReceiver(mBluetoothStateReceiver, btIntent)
            } else {
                Toast.makeText(this, "Bluetooth ON", Toast.LENGTH_LONG).show()
            }
        }
    }
}