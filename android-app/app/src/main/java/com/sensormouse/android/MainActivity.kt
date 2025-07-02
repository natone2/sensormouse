package com.sensormouse.android

import android.Manifest
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.sensormouse.android.billing.BillingManager
import com.sensormouse.android.databinding.ActivityMainBinding
import com.sensormouse.android.premium.PremiumManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.Socket
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), SensorEventListener {
    
    companion object {
        private const val TAG = "MainActivity"
        private const val PERMISSION_REQUEST_CODE = 123
        private const val SESSION_UPDATE_INTERVAL = 1000L // 1 segundo
    }
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var sensorManager: SensorManager
    private lateinit var gyroscope: Sensor
    private lateinit var accelerometer: Sensor
    
    private var socket: Socket? = null
    private var isConnected = false
    private var isActive = false
    
    // Monetización
    private lateinit var billingManager: BillingManager
    private lateinit var premiumManager: PremiumManager
    private var sessionUpdateHandler = Handler(Looper.getMainLooper())
    private var sessionUpdateRunnable: Runnable? = null
    
    // Sensores
    private var gyroX = 0f
    private var gyroY = 0f
    private var accelZ = 0f
    private var sensitivity = 1.0f
    
    // Calibración
    private var calibratedGyroX = 0f
    private var calibratedGyroY = 0f
    private var calibratedAccelZ = 0f
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Inicializar monetización
        initializeMonetization()
        
        // Inicializar sensores
        initializeSensors()
        
        // Configurar UI
        setupUI()
        
        // Inicializar publicidad
        initializeAds()
        
        // Iniciar actualización de sesión
        startSessionUpdates()
    }
    
    private fun initializeMonetization() {
        billingManager = BillingManager(this)
        premiumManager = PremiumManager(this)
        
        // Observar cambios en el estado de usuario pro
        lifecycleScope.launch {
            billingManager.isProUser.collectLatest { isPro ->
                premiumManager.setProUser(isPro)
                updateUIForUserType(isPro)
            }
        }
        
        // Observar estado de compra
        lifecycleScope.launch {
            billingManager.purchaseStatus.collectLatest { status ->
                when (status) {
                    is com.sensormouse.android.billing.PurchaseStatus.Purchased -> {
                        Toast.makeText(this@MainActivity, "¡Gracias por comprar SensorMouse Pro!", Toast.LENGTH_LONG).show()
                    }
                    is com.sensormouse.android.billing.PurchaseStatus.Error -> {
                        Toast.makeText(this@MainActivity, "Error: ${status.message}", Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }
    }
    
    private fun initializeSensors() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)!!
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!!
        
        if (gyroscope == null || accelerometer == null) {
            Toast.makeText(this, "Sensores no disponibles", Toast.LENGTH_LONG).show()
            finish()
        }
    }
    
    private fun setupUI() {
        // Configurar SeekBar de sensibilidad
        binding.sensitivitySeekBar.setOnSeekBarChangeListener(object : android.widget.SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: android.widget.SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    val minSensitivity = premiumManager.getMinSensitivity()
                    val maxSensitivity = premiumManager.getMaxSensitivity()
                    sensitivity = minSensitivity + (progress / 50f) * (maxSensitivity - minSensitivity)
                    binding.sensitivityValueText.text = "${String.format("%.1f", sensitivity)}x"
                }
            }
            
            override fun onStartTrackingTouch(seekBar: android.widget.SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: android.widget.SeekBar?) {}
        })
        
        // Configurar botones
        binding.connectButton.setOnClickListener { toggleConnection() }
        binding.calibrateButton.setOnClickListener { calibrateSensors() }
        binding.upgradeButton.setOnClickListener { showUpgradeDialog() }
        
        // Configurar botones de ratón
        binding.leftClickButton.setOnClickListener { sendMouseClick("left") }
        binding.middleClickButton.setOnClickListener { sendMouseClick("middle") }
        binding.rightClickButton.setOnClickListener { sendMouseClick("right") }
        
        // Configurar límites iniciales según tipo de usuario
        updateUIForUserType(premiumManager.isProUser.value)
    }
    
    private fun updateUIForUserType(isPro: Boolean) {
        if (isPro) {
            // Usuario Pro - sin limitaciones
            binding.sessionInfoCard.visibility = View.GONE
            binding.upgradeButton.visibility = View.GONE
            binding.adBanner.visibility = View.GONE
        } else {
            // Usuario gratuito - mostrar limitaciones
            binding.sessionInfoCard.visibility = View.VISIBLE
            binding.upgradeButton.visibility = View.VISIBLE
            binding.adBanner.visibility = View.VISIBLE
            
            // Configurar límites de sensibilidad
            val minSensitivity = premiumManager.getMinSensitivity()
            val maxSensitivity = premiumManager.getMaxSensitivity()
            binding.sensitivitySeekBar.max = 50
            binding.sensitivitySeekBar.progress = 10 // 1.0x por defecto
        }
    }
    
    private fun initializeAds() {
        MobileAds.initialize(this) {}
        
        val adRequest = AdRequest.Builder().build()
        binding.adBanner.loadAd(adRequest)
    }
    
    private fun startSessionUpdates() {
        sessionUpdateRunnable = object : Runnable {
            override fun run() {
                updateSessionInfo()
                sessionUpdateHandler.postDelayed(this, SESSION_UPDATE_INTERVAL)
            }
        }
        sessionUpdateHandler.post(sessionUpdateRunnable!!)
    }
    
    private fun updateSessionInfo() {
        if (!premiumManager.isProUser.value) {
            premiumManager.updateUsageTime()
            
            val remainingDays = premiumManager.getRemainingTrialDays()
            val progress = premiumManager.getTrialProgress()
            
            // Actualizar UI
            binding.sessionProgressBar.progress = (progress * 100).toInt()
            binding.sessionTimeText.text = "$remainingDays días restantes"
            
            // Verificar si el trial expiró
            if (premiumManager.isTrialExpired()) {
                showTrialExpiredDialog()
            }
        }
    }
    
    private fun formatTime(minutes: Long): String {
        val hours = minutes / 60
        val mins = minutes % 60
        return if (hours > 0) {
            String.format("%d:%02d", hours, mins)
        } else {
            String.format("%d:%02d", mins, 0)
        }
    }
    
    private fun showTrialExpiredDialog() {
        AlertDialog.Builder(this)
            .setTitle("Período de Prueba Expirado")
            .setMessage("Has completado los 30 días de prueba gratuita. ¿Quieres actualizar a SensorMouse Pro para seguir usando la app?")
            .setPositiveButton("Upgrade a Pro") { _, _ ->
                showUpgradeDialog()
            }
            .setNegativeButton("Más Tarde") { _, _ ->
                // El usuario puede seguir viendo la app pero no usarla
            }
            .setCancelable(false)
            .show()
    }
    
    private fun showUpgradeDialog() {
        AlertDialog.Builder(this)
            .setTitle("🚀 SensorMouse Pro")
            .setMessage("""
                Desbloquea todas las funcionalidades:
                
                ✅ Uso ilimitado (sin límite de 30 días)
                ✅ Sensibilidad personalizable (0.1x - 5.0x)
                ✅ Calibración avanzada con múltiples puntos
                ✅ Múltiples perfiles guardados
                ✅ Sin publicidad
                ✅ Temas personalizados
                ✅ Estadísticas de uso
                ✅ Backup en la nube
                
                Precio: €3.99 (pago único)
            """.trimIndent())
            .setPositiveButton("Comprar Ahora") { _, _ ->
                billingManager.launchBillingFlow(this)
            }
            .setNegativeButton("Más Tarde", null)
            .show()
    }
    
    private fun toggleConnection() {
        if (!isConnected) {
            connectToServer()
        } else {
            disconnectFromServer()
        }
    }
    
    private fun connectToServer() {
        val serverIp = binding.serverIpInput.text.toString()
        val serverPort = binding.serverPortInput.text.toString().toIntOrNull() ?: 5000
        
        if (serverIp.isEmpty()) {
            Toast.makeText(this, "Introduce la IP del servidor", Toast.LENGTH_SHORT).show()
            return
        }
        
        lifecycleScope.launch {
            try {
                socket = Socket(serverIp, serverPort)
                isConnected = true
                updateConnectionStatus(true)
                Toast.makeText(this@MainActivity, "Conectado al servidor", Toast.LENGTH_SHORT).show()
                
                // Iniciar envío de datos
                startDataTransmission()
                
            } catch (e: IOException) {
                Log.e(TAG, "Error conectando al servidor", e)
                Toast.makeText(this@MainActivity, "Error conectando al servidor", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun disconnectFromServer() {
        try {
            socket?.close()
            socket = null
            isConnected = false
            isActive = false
            updateConnectionStatus(false)
            Toast.makeText(this, "Desconectado del servidor", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Log.e(TAG, "Error desconectando del servidor", e)
        }
    }
    
    private fun updateConnectionStatus(connected: Boolean) {
        if (connected) {
            binding.statusText.text = "Conectado"
            binding.statusIcon.setImageResource(R.drawable.ic_status_online)
            binding.connectButton.text = "Desconectar"
        } else {
            binding.statusText.text = "Desconectado"
            binding.statusIcon.setImageResource(R.drawable.ic_status_offline)
            binding.connectButton.text = "Conectar"
        }
    }
    
    private fun calibrateSensors() {
        if (!checkPermissions()) {
            requestPermissions()
            return
        }
        
        Toast.makeText(this, "Mantén el dispositivo quieto durante 3 segundos", Toast.LENGTH_LONG).show()
        
        lifecycleScope.launch {
            delay(3000)
            
            calibratedGyroX = gyroX
            calibratedGyroY = gyroY
            calibratedAccelZ = accelZ
            
            Toast.makeText(this@MainActivity, "Calibración completada", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun startDataTransmission() {
        if (!checkPermissions()) {
            requestPermissions()
            return
        }
        
        isActive = true
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_GAME)
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
    }
    
    private fun sendMouseClick(button: String) {
        if (!isConnected) {
            Toast.makeText(this, "No conectado al servidor", Toast.LENGTH_SHORT).show()
            return
        }
        
        lifecycleScope.launch {
            try {
                val message = "CLICK:$button\n"
                socket?.getOutputStream()?.write(message.toByteArray())
            } catch (e: IOException) {
                Log.e(TAG, "Error enviando clic", e)
                disconnectFromServer()
            }
        }
    }
    
    override fun onSensorChanged(event: SensorEvent?) {
        if (!isActive || !isConnected) return
        
        event?.let {
            when (it.sensor.type) {
                Sensor.TYPE_GYROSCOPE -> {
                    gyroX = it.values[0]
                    gyroY = it.values[1]
                }
                Sensor.TYPE_ACCELEROMETER -> {
                    accelZ = it.values[2]
                }
            }
            
            // Enviar datos al servidor
            sendSensorData()
        }
    }
    
    private fun sendSensorData() {
        val deltaX = (gyroX - calibratedGyroX) * sensitivity
        val deltaY = (gyroY - calibratedGyroY) * sensitivity
        
        val message = "MOVE:$deltaX,$deltaY\n"
        
        lifecycleScope.launch {
            try {
                socket?.getOutputStream()?.write(message.toByteArray())
            } catch (e: IOException) {
                Log.e(TAG, "Error enviando datos de sensor", e)
                disconnectFromServer()
            }
        }
    }
    
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    
    private fun checkPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED
    }
    
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
            PERMISSION_REQUEST_CODE
        )
    }
    
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permisos concedidos", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permisos necesarios para el funcionamiento", Toast.LENGTH_LONG).show()
            }
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        
        // Limpiar recursos
        sessionUpdateRunnable?.let { sessionUpdateHandler.removeCallbacks(it) }
        sensorManager.unregisterListener(this)
        disconnectFromServer()
        billingManager.disconnect()
    }
} 