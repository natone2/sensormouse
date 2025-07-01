"""
Utilidades para SensorMouse Server
Funciones auxiliares y helpers
"""

import math
import time
from typing import Dict, Tuple, List, Union
try:
    import numpy as np
    NUMPY_AVAILABLE = True
except ImportError:
    NUMPY_AVAILABLE = False

def clamp(value: float, min_val: float, max_val: float) -> float:
    """Limita un valor entre un mínimo y máximo"""
    return max(min_val, min(max_val, value))

def calculate_distance(point1: Tuple[float, float], point2: Tuple[float, float]) -> float:
    """Calcula la distancia euclidiana entre dos puntos"""
    return math.sqrt((point2[0] - point1[0])**2 + (point2[1] - point1[1])**2)

def smooth_data(data: List[float], window_size: int = 5) -> List[float]:
    """Aplica suavizado de media móvil a una lista de datos"""
    if len(data) < window_size:
        return data
    
    if not NUMPY_AVAILABLE:
        # Implementación sin numpy
        smoothed = []
        for i in range(len(data)):
            start = max(0, i - window_size // 2)
            end = min(len(data), i + window_size // 2 + 1)
            window_data = data[start:end]
            smoothed.append(sum(window_data) / len(window_data))
        return smoothed
    
    smoothed = []
    for i in range(len(data)):
        start = max(0, i - window_size // 2)
        end = min(len(data), i + window_size // 2 + 1)
        smoothed.append(np.mean(data[start:end]))
    
    return smoothed

def normalize_angle(angle: float) -> float:
    """Normaliza un ángulo al rango [-π, π]"""
    while angle > math.pi:
        angle -= 2 * math.pi
    while angle < -math.pi:
        angle += 2 * math.pi
    return angle

def quaternion_to_euler(q: Dict[str, float]) -> Dict[str, float]:
    """Convierte cuaterniones a ángulos de Euler"""
    x, y, z, w = q['x'], q['y'], q['z'], q['w']
    
    # Roll (x-axis rotation)
    sinr_cosp = 2 * (w * x + y * z)
    cosr_cosp = 1 - 2 * (x * x + y * y)
    roll = math.atan2(sinr_cosp, cosr_cosp)
    
    # Pitch (y-axis rotation)
    sinp = 2 * (w * y - z * x)
    if abs(sinp) >= 1:
        pitch = math.copysign(math.pi / 2, sinp)
    else:
        pitch = math.asin(sinp)
    
    # Yaw (z-axis rotation)
    siny_cosp = 2 * (w * z + x * y)
    cosy_cosp = 1 - 2 * (y * y + z * z)
    yaw = math.atan2(siny_cosp, cosy_cosp)
    
    return {
        'roll': math.degrees(roll),
        'pitch': math.degrees(pitch),
        'yaw': math.degrees(yaw)
    }

def get_screen_info() -> Dict[str, Union[int, str]]:
    """Obtiene información de la pantalla"""
    try:
        import pyautogui
        size = pyautogui.size()
        return {
            'width': size.width,
            'height': size.height
        }
    except ImportError:
        return {'width': 1920, 'height': 1080}  # Valores por defecto

def format_bytes(bytes_value: Union[int, float]) -> str:
    """Formatea bytes en formato legible"""
    value = float(bytes_value)
    for unit in ['B', 'KB', 'MB', 'GB']:
        if value < 1024.0:
            return f"{value:.1f} {unit}"
        value /= 1024.0
    return f"{value:.1f} TB"

def get_timestamp() -> str:
    """Obtiene timestamp formateado"""
    return time.strftime("%Y-%m-%d %H:%M:%S")

def validate_ip(ip: str) -> bool:
    """Valida formato de IP"""
    try:
        parts = ip.split('.')
        if len(parts) != 4:
            return False
        for part in parts:
            if not 0 <= int(part) <= 255:
                return False
        return True
    except (ValueError, AttributeError):
        return False

def validate_port(port: int) -> bool:
    """Valida número de puerto"""
    return 1 <= port <= 65535 