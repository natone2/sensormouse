package com.sensormouse.android.premium

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.TimeUnit

class PremiumManager(context: Context) {
    
    companion object {
        private const val PREFS_NAME = "premium_prefs"
        private const val KEY_IS_PRO = "is_pro_user"
        private const val KEY_FIRST_LAUNCH_DATE = "first_launch_date"
        private const val KEY_TOTAL_USAGE_TIME = "total_usage_time"
        private const val KEY_SENSITIVITY_PROFILES = "sensitivity_profiles"
        private const val KEY_CALIBRATION_POINTS = "calibration_points"
        
        // Límites de la versión gratuita
        private const val FREE_TRIAL_DAYS = 30L
        private const val FREE_SENSITIVITY_RANGE = 1.0f
        private const val FREE_CALIBRATION_POINTS = 1
        private const val FREE_PROFILES_LIMIT = 1
    }
    
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    
    private val _isProUser = MutableStateFlow(false)
    val isProUser: StateFlow<Boolean> = _isProUser.asStateFlow()
    
    private val _trialDaysRemaining = MutableStateFlow(FREE_TRIAL_DAYS)
    val trialDaysRemaining: StateFlow<Long> = _trialDaysRemaining.asStateFlow()
    
    private val _totalUsageTime = MutableStateFlow(0L)
    val totalUsageTime: StateFlow<Long> = _totalUsageTime.asStateFlow()
    
    init {
        loadUserStatus()
        checkTrialStatus()
    }
    
    private fun loadUserStatus() {
        _isProUser.value = prefs.getBoolean(KEY_IS_PRO, false)
        _totalUsageTime.value = prefs.getLong(KEY_TOTAL_USAGE_TIME, 0L)
    }
    
    fun setProUser(isPro: Boolean) {
        _isProUser.value = isPro
        prefs.edit { putBoolean(KEY_IS_PRO, isPro) }
    }
    
    private fun checkTrialStatus() {
        if (isProUser.value) {
            // Usuario Pro - sin limitaciones
            _trialDaysRemaining.value = Long.MAX_VALUE
            return
        }
        
        val firstLaunchDate = prefs.getLong(KEY_FIRST_LAUNCH_DATE, 0L)
        if (firstLaunchDate == 0L) {
            // Primera vez que se abre la app
            prefs.edit { putLong(KEY_FIRST_LAUNCH_DATE, System.currentTimeMillis()) }
            _trialDaysRemaining.value = FREE_TRIAL_DAYS
        } else {
            // Calcular días restantes
            val elapsedDays = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - firstLaunchDate)
            val remaining = FREE_TRIAL_DAYS - elapsedDays
            _trialDaysRemaining.value = if (remaining > 0) remaining else 0
        }
    }
    
    fun updateUsageTime() {
        if (!isProUser.value) {
            // Actualizar tiempo total de uso
            val totalTime = prefs.getLong(KEY_TOTAL_USAGE_TIME, 0L) + 1
            prefs.edit { putLong(KEY_TOTAL_USAGE_TIME, totalTime) }
            _totalUsageTime.value = totalTime
        }
    }
    
    // Funcionalidades Premium
    
    fun canUseUnlimitedTime(): Boolean = isProUser.value || !isTrialExpired()
    
    fun canUseAdvancedSensitivity(): Boolean = isProUser.value
    
    fun canUseMultipleProfiles(): Boolean = isProUser.value
    
    fun canUseAdvancedCalibration(): Boolean = isProUser.value
    
    fun canUseCustomThemes(): Boolean = isProUser.value
    
    fun canUseStatistics(): Boolean = isProUser.value
    
    fun canUseCloudBackup(): Boolean = isProUser.value
    
    fun canUseCustomGestures(): Boolean = isProUser.value
    
    // Límites específicos
    
    fun getMaxSensitivity(): Float = if (isProUser.value) 5.0f else FREE_SENSITIVITY_RANGE
    
    fun getMinSensitivity(): Float = if (isProUser.value) 0.1f else FREE_SENSITIVITY_RANGE
    
    fun getMaxCalibrationPoints(): Int = if (isProUser.value) 10 else FREE_CALIBRATION_POINTS
    
    fun getMaxProfiles(): Int = if (isProUser.value) 10 else FREE_PROFILES_LIMIT
    
    // Verificaciones de uso
    
    fun isTrialExpired(): Boolean {
        if (isProUser.value) return false
        return _trialDaysRemaining.value <= 0
    }
    
    fun getRemainingTrialDays(): Long = _trialDaysRemaining.value
    
    fun getTrialProgress(): Float {
        if (isProUser.value) return 1.0f
        val used = FREE_TRIAL_DAYS - _trialDaysRemaining.value
        return (used.toFloat() / FREE_TRIAL_DAYS).coerceIn(0f, 1f)
    }
    
    // Estadísticas de uso
    
    fun getTotalUsageHours(): Float {
        return _totalUsageTime.value / 60f
    }
    
    fun getUsageStreak(): Int {
        // Implementar lógica de racha de uso
        return prefs.getInt("usage_streak", 0)
    }
    
    fun incrementUsageStreak() {
        val currentStreak = prefs.getInt("usage_streak", 0)
        prefs.edit { putInt("usage_streak", currentStreak + 1) }
    }
    
    // Gestión de perfiles
    
    fun saveSensitivityProfile(name: String, sensitivity: Float): Boolean {
        if (!isProUser.value) {
            val profiles = getSensitivityProfiles()
            if (profiles.size >= FREE_PROFILES_LIMIT) return false
        }
        
        val profiles = getSensitivityProfiles().toMutableMap()
        profiles[name] = sensitivity
        saveSensitivityProfiles(profiles)
        return true
    }
    
    fun getSensitivityProfiles(): Map<String, Float> {
        val profilesJson = prefs.getString(KEY_SENSITIVITY_PROFILES, "{}")
        return try {
            // Implementar parsing de JSON simple
            mapOf("Default" to 1.0f)
        } catch (e: Exception) {
            mapOf("Default" to 1.0f)
        }
    }
    
    private fun saveSensitivityProfiles(profiles: Map<String, Float>) {
        // Implementar serialización simple
        prefs.edit { putString(KEY_SENSITIVITY_PROFILES, "{}") }
    }
    
    // Calibración avanzada
    
    fun saveCalibrationPoint(point: CalibrationPoint): Boolean {
        if (!isProUser.value) {
            val points = getCalibrationPoints()
            if (points.size >= FREE_CALIBRATION_POINTS) return false
        }
        
        val points = getCalibrationPoints().toMutableList()
        points.add(point)
        saveCalibrationPoints(points)
        return true
    }
    
    fun getCalibrationPoints(): List<CalibrationPoint> {
        // Implementar carga de puntos de calibración
        return emptyList()
    }
    
    private fun saveCalibrationPoints(points: List<CalibrationPoint>) {
        // Implementar guardado de puntos de calibración
    }
}

data class CalibrationPoint(
    val x: Float,
    val y: Float,
    val z: Float,
    val timestamp: Long = System.currentTimeMillis()
) 