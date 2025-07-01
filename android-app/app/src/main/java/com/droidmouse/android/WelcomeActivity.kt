package com.droidmouse.android

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Bundle
import android.text.format.Formatter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val ipValue = findViewById<TextView>(R.id.ipValue)
        val btnCopyIp = findViewById<Button>(R.id.btnCopyIp)
        val btnContinue = findViewById<Button>(R.id.btnContinue)

        // Obtener la IP local
        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val ipAddress = Formatter.formatIpAddress(wifiManager.connectionInfo.ipAddress)
        ipValue.text = ipAddress

        btnCopyIp.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("IP", ipAddress)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "IP copiada al portapapeles", Toast.LENGTH_SHORT).show()
        }

        btnContinue.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
} 