package com.sensormouse.android

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sensormouse.android.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.io.OutputStream
import java.net.Socket
import java.net.SocketException
import kotlin.math.abs

class MainActivity : AppCompatActivity(), SensorEventListener {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var sensorManager: SensorManager
    private var gyroscopeSensor: Sensor? = null
    private var accelerometerSensor: Sensor? = null
    
    private var socket: Socket? = null
    private var outputStream: OutputStream? = null
    private var isConnected = false
    private var isActive = false
    
    private var serverIp = "192.168.1.100"
    private var serverPort = 8080
    
    // Filtros de Kalman para suavizado
    private val kalmanX = KalmanFilter()
    private val kalmanY = KalmanFilter()
    
    // Calibración
    private val calibrationSamples = mutableListOf<SensorData>()
    private var isCalibrated = false
    private var gyroBias = SensorData(0f, 0f, 0f)
    
    // Configuración
    private val sensorDelay = SensorManager.SENSOR_DELAY_GAME
    private val sampleRate = 50 // Hz
    
    companion object {
        private const val TAG = "SensorMouse"
        private const val CALIBRATION_SAMPLES = 100
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupSensors()
        setupUI()
        setupClickListeners()

        // --- NUEVO: Enlazar botones de mouse ---
        binding.btnLeft.setOnClickListener {
            animateMouseButton(binding.btnLeft)
            sendCommand("mouse_left_click")
        }
        binding.btnMiddle.setOnClickListener {
            animateMouseButton(binding.btnMiddle)
            sendCommand("mouse_middle_click")
        }
        binding.btnRight.setOnClickListener {
            animateMouseButton(binding.btnRight)
            sendCommand("mouse_right_click")
        }
    }
    
    private fun setupSensors() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        
        if (gyroscopeSensor == null || accelerometerSensor == null) {
            Toast.makeText(this, "Sensores no disponibles", Toast.LENGTH_LONG).show()
            finish()
        }
    }
    
    private fun setupUI() {
        binding.apply {
            statusText.text = "Desconectado"
            serverIpInput.setText(serverIp)
            serverPortInput.setText(serverPort.toString())
            sensitivitySlider.value = 1.0f
        }
    }
    
    private fun setupClickListeners() {
        binding.apply {
            connectButton.setOnClickListener {
                if (isConnected) {
                    disconnect()
                } else {
                    connect()
                }
            }
            
            calibrateButton.setOnClickListener {
                startCalibration()
            }
            
            activateButton.setOnClickListener {
                if (isConnected) {
                    toggleActivation()
                } else {
                    Toast.makeText(this@MainActivity, "Conecta primero al servidor", Toast.LENGTH_SHORT).show()
                }
            }
            
            sensitivitySlider.addOnChangeListener { _, value, fromUser ->
                if (fromUser) {
                    updateSensitivity(value)
                }
            }
        }
    }
    
    private fun connect() {
        try {
            serverIp = binding.serverIpInput.text.toString()
            serverPort = binding.serverPortInput.text.toString().toInt()
            
            if (serverIp.isEmpty() || serverPort <= 0) {
                Toast.makeText(this, "IP o puerto inválido", Toast.LENGTH_SHORT).show()
                return
            }
            
            binding.connectButton.isEnabled = false
            binding.statusText.text = "Conectando..."
            
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    socket = Socket(serverIp, serverPort)
                    outputStream = socket?.getOutputStream()
                    isConnected = true
                    
                    runOnUiThread {
                        binding.statusText.text = "Conectado"
                        binding.connectButton.text = "Desconectar"
                        binding.connectButton.isEnabled = true
                        Toast.makeText(this@MainActivity, "Conectado al servidor", Toast.LENGTH_SHORT).show()
                    }
                    
                    // Iniciar envío de datos
                    startSensorDataTransmission()
                    
                } catch (e: Exception) {
                    runOnUiThread {
                        binding.statusText.text = "Error de conexión"
                        binding.connectButton.isEnabled = true
                        Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
            
        } catch (e: Exception) {
            runOnUiThread {
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                binding.connectButton.isEnabled = true
            }
        }
    }
    
    private fun disconnect() {
        isConnected = false
        isActive = false
        
        try {
            socket?.close()
            outputStream?.close()
        } catch (e: Exception) {
            Log.e(TAG, "Error cerrando conexión", e)
        }
        
        socket = null
        outputStream = null
        
        binding.apply {
            statusText.text = "Desconectado"
            connectButton.text = "Conectar"
            activateButton.text = "Activar"
        }
        
        runOnUiThread {
            Toast.makeText(this, "Desconectado", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun startCalibration() {
        if (!isConnected) {
            Toast.makeText(this, "Conecta primero al servidor", Toast.LENGTH_SHORT).show()
            return
        }
        
        calibrationSamples.clear()
        isCalibrated = false
        binding.calibrateButton.text = "Calibrando..."
        binding.calibrateButton.isEnabled = false
        
        Toast.makeText(this, "Mantén el dispositivo quieto durante 2 segundos", Toast.LENGTH_LONG).show()
        
        lifecycleScope.launch {
            delay(2000L) // 2 segundos de calibración
            
            if (calibrationSamples.size >= CALIBRATION_SAMPLES) {
                calculateBias()
                isCalibrated = true
                binding.calibrateButton.text = "Recalibrar"
                binding.calibrateButton.isEnabled = true
                Toast.makeText(this@MainActivity, "Calibración completada", Toast.LENGTH_SHORT).show()
            } else {
                binding.calibrateButton.text = "Calibrar"
                binding.calibrateButton.isEnabled = true
                Toast.makeText(this@MainActivity, "Calibración fallida", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun calculateBias() {
        if (calibrationSamples.isEmpty()) return
        
        val avgX = calibrationSamples.map { it.x }.average().toFloat()
        val avgY = calibrationSamples.map { it.y }.average().toFloat()
        val avgZ = calibrationSamples.map { it.z }.average().toFloat()
        
        gyroBias = SensorData(avgX, avgY, avgZ)
        Log.d(TAG, "Bias calculado: $gyroBias")
    }
    
    private fun toggleActivation() {
        isActive = !isActive
        
        binding.activateButton.text = if (isActive) "Desactivar" else "Activar"
        
        val command = if (isActive) "activate" else "deactivate"
        sendCommand(command)
        
        Toast.makeText(this, if (isActive) "Control activado" else "Control desactivado", Toast.LENGTH_SHORT).show()
    }
    
    private fun updateSensitivity(value: Float) {
        val sensitivity = value * 2.0f // Escalar de 0-1 a 0-2
        sendCommand("sensitivity:$sensitivity")
    }
    
    private fun startSensorDataTransmission() {
        lifecycleScope.launch(Dispatchers.IO) {
            while (isConnected) {
                try {
                    delay((1000L / sampleRate)) // 50 Hz
                    
                    if (isActive && isCalibrated) {
                        // Los datos se envían en onSensorChanged
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error en transmisión", e)
                    break
                }
            }
        }
    }
    
    private fun sendSensorData(gyro: SensorData, accel: SensorData) {
        if (!isConnected || !isActive) return
        
        try {
            val data = mapOf(
                "type" to "sensor_data",
                "gyro" to mapOf(
                    "x" to gyro.x,
                    "y" to gyro.y,
                    "z" to gyro.z
                ),
                "accel" to mapOf(
                    "x" to accel.x,
                    "y" to accel.y,
                    "z" to accel.z
                )
            )
            
            val json = com.google.gson.Gson().toJson(data)
            outputStream?.write("$json\n".toByteArray())
            outputStream?.flush()
            
        } catch (e: Exception) {
            Log.e(TAG, "Error enviando datos", e)
            if (e is SocketException) {
                runOnUiThread {
                    disconnect()
                }
            }
        }
    }
    
    private fun sendCommand(command: String) {
        if (!isConnected) return
        
        try {
            val data = mapOf(
                "type" to "command",
                "command" to command
            )
            
            val json = com.google.gson.Gson().toJson(data)
            outputStream?.write("$json\n".toByteArray())
            outputStream?.flush()
            
        } catch (e: Exception) {
            Log.e(TAG, "Error enviando comando", e)
        }
    }
    
    override fun onResume() {
        super.onResume()
        gyroscopeSensor?.let { sensor ->
            sensorManager.registerListener(this, sensor, sensorDelay)
        }
        accelerometerSensor?.let { sensor ->
            sensorManager.registerListener(this, sensor, sensorDelay)
        }
    }
    
    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
    
    override fun onDestroy() {
        super.onDestroy()
        disconnect()
    }
    
    override fun onSensorChanged(event: SensorEvent?) {
        event?.let { sensorEvent ->
            when (sensorEvent.sensor.type) {
                Sensor.TYPE_GYROSCOPE -> {
                    val gyroData = SensorData(
                        sensorEvent.values[0],
                        sensorEvent.values[1],
                        sensorEvent.values[2]
                    )
                    
                    // Aplicar calibración
                    val calibratedGyro = if (isCalibrated) {
                        SensorData(
                            gyroData.x - gyroBias.x,
                            gyroData.y - gyroBias.y,
                            gyroData.z - gyroBias.z
                        )
                    } else {
                        gyroData
                    }
                    
                    // Aplicar filtro de Kalman
                    val filteredX = kalmanX.update(calibratedGyro.x)
                    val filteredY = kalmanY.update(calibratedGyro.y)
                    
                    val filteredGyro = SensorData(filteredX, filteredY, calibratedGyro.z)
                    
                    // Guardar para calibración
                    if (!isCalibrated && calibrationSamples.size < CALIBRATION_SAMPLES) {
                        calibrationSamples.add(gyroData)
                    }
                    
                    // Enviar datos si está activo
                    if (isActive && isCalibrated) {
                        sendSensorData(filteredGyro, lastAccelData)
                    }

                    // Actualizar estadísticas
                    updateSensorStats(gyroData, lastAccelData)
                }
                
                Sensor.TYPE_ACCELEROMETER -> {
                    lastAccelData = SensorData(
                        sensorEvent.values[0],
                        sensorEvent.values[1],
                        sensorEvent.values[2]
                    )

                    // Actualizar estadísticas
                    updateSensorStats(lastGyroData, lastAccelData)
                }
            }
        }
    }
    
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No necesitamos manejar cambios de precisión
    }
    
    private var lastAccelData = SensorData(0f, 0f, 0f)
    private var lastGyroData = SensorData(0f, 0f, 0f)
    
    data class SensorData(val x: Float, val y: Float, val z: Float)
    
    class KalmanFilter {
        private var estimate = 0.0
        private var estimateError = 1.0
        private val processVariance = 1e-5
        private val measurementVariance = 1e-2
        
        fun update(measurement: Float): Float {
            val predictionError = estimateError + processVariance
            val kalmanGain = predictionError / (predictionError + measurementVariance)
            
            estimate = estimate + kalmanGain * (measurement - estimate)
            estimateError = (1 - kalmanGain) * predictionError
            
            return estimate.toFloat()
        }
    }

    // --- NUEVO: Animación visual al pulsar botón de mouse ---
    private fun animateMouseButton(button: View) {
        button.animate().scaleX(0.85f).scaleY(0.85f).setDuration(80).withEndAction {
            button.animate().scaleX(1f).scaleY(1f).setDuration(80).start()
        }.start()
    }

    // --- NUEVO: Actualizar estadísticas de sensores en tiempo real ---
    private fun updateSensorStats(gyro: SensorData, accel: SensorData) {
        runOnUiThread {
            binding.gyroXText.text = "Gyro X: %.2f".format(gyro.x)
            binding.gyroYText.text = "Gyro Y: %.2f".format(gyro.y)
            binding.accelZText.text = "Accel Z: %.2f".format(accel.z)
        }
    }
} 