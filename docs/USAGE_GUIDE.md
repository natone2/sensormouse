# 📱 Guía de Uso - SensorMouse

## 🎯 ¿Cómo funciona?

SensorMouse convierte tu smartphone en un ratón inalámbrico usando los sensores del dispositivo. Aquí te explicamos paso a paso cómo usarlo.

---

## 📋 Requisitos Previos

### Dispositivos necesarios:
- **Smartphone Android** con giroscopio y acelerómetro
- **PC/Laptop** con Python 3.8+
- **Red WiFi** compartida entre ambos dispositivos

### Versiones compatibles:
- Android 6.0 (API 23) o superior
- Python 3.8+ en PC
- Windows 10/11, macOS 10.14+, Linux

---

## 🖥️ Paso 1: Instalar el Servidor

### En tu PC:

```bash
# 1. Descargar el proyecto
git clone https://github.com/natone2/sensormouse.git
cd sensormouse/server

# 2. Crear entorno virtual
python -m venv venv
source venv/bin/activate  # Windows: venv\Scripts\activate

# 3. Instalar dependencias
pip install -r requirements.txt

# 4. Ejecutar servidor
python main.py
```

**Resultado esperado:**
```
🚀 Servidor SensorMouse iniciado
📡 Escuchando en puerto 5000
🌐 IP del servidor: 192.168.1.100
✅ Listo para conexiones
```

---

## 📱 Paso 2: Instalar la App

### Opción A: Google Play Store (Recomendado)
1. Busca "SensorMouse" en Google Play Store
2. Instala la aplicación
3. Abre la app

### Opción B: APK directo
1. Descarga el APK desde [releases](https://github.com/natone2/sensormouse/releases)
2. Habilita "Instalar apps de orígenes desconocidos"
3. Instala el APK
4. Abre la app

---

## 🔗 Paso 3: Configurar Conexión

### En la app Android:

1. **Abrir SensorMouse**
   - Verás la pantalla principal con el estado "Desconectado"

2. **Configurar servidor**
   - IP del servidor: `192.168.1.100` (o la IP de tu PC)
   - Puerto: `5000`

3. **Conectar**
   - Toca el botón "Conectar"
   - El estado cambiará a "Conectado" ✅

---

## 🎮 Paso 4: Calibrar Sensores

### Antes de usar:

1. **Mantén el dispositivo quieto**
   - Colócalo sobre una superficie plana
   - No lo muevas durante la calibración

2. **Toca "Calibrar"**
   - Espera 3 segundos
   - Verás "Calibración completada"

3. **Ajustar sensibilidad**
   - Usa el slider para ajustar la sensibilidad
   - Recomendado: 1.0x para empezar

---

## 🖱️ Paso 5: Usar como Ratón

### Movimiento del cursor:
- **Inclina el dispositivo** hacia donde quieras mover el cursor
- **Movimientos suaves** = mejor precisión
- **Evita movimientos bruscos**

### Botones del ratón:
- **Botón izquierdo**: Toca el icono del ratón izquierdo
- **Botón derecho**: Toca el icono del ratón derecho  
- **Botón central**: Toca el icono del ratón central

---

## ⚙️ Configuración Avanzada

### Sensibilidad:
- **0.1x - 0.5x**: Movimientos muy precisos (dibujo, edición)
- **0.5x - 1.0x**: Uso general (navegación web)
- **1.0x - 2.0x**: Presentaciones, gaming
- **2.0x - 5.0x**: Movimientos rápidos (solo Pro)

### Perfiles guardados:
- **Versión Pro**: Guarda múltiples configuraciones
- **Cambio rápido** entre diferentes usos

---

## 🎯 Casos de Uso

### 📊 Presentaciones:
- Controla PowerPoint/Keynote desde el móvil
- Sensibilidad: 1.5x - 2.0x
- Ideal para presentaciones profesionales

### 🎮 Gaming:
- Controla juegos simples desde el sofá
- Sensibilidad: 0.8x - 1.2x
- Perfecto para juegos de estrategia

### 🎨 Diseño/Edición:
- Control preciso para Photoshop, Illustrator
- Sensibilidad: 0.3x - 0.8x
- Calibración frecuente recomendada

### 📺 Media Center:
- Controla reproductores multimedia
- Sensibilidad: 1.0x - 1.5x
- Ideal para ver películas/series

---

## 🔧 Solución de Problemas

### La app no se conecta:
- ✅ Verifica que ambos dispositivos estén en la misma red WiFi
- ✅ Comprueba que el servidor esté ejecutándose
- ✅ Revisa la IP del servidor
- ✅ Verifica que el puerto 5000 esté libre

### Movimiento impreciso:
- ✅ Recalibra los sensores
- ✅ Ajusta la sensibilidad
- ✅ Evita interferencias magnéticas
- ✅ Mantén el dispositivo estable

### Latencia alta:
- ✅ Usa red WiFi 5GHz si es posible
- ✅ Cierra apps innecesarias en el móvil
- ✅ Reduce la distancia entre dispositivos
- ✅ Verifica la velocidad de tu red

---

## 💡 Consejos Pro

### Para mejor precisión:
- Calibra en el mismo entorno donde usarás la app
- Evita usar cerca de imanes o metales
- Mantén el dispositivo a temperatura estable
- Usa movimientos suaves y controlados

### Para presentaciones:
- Practica antes de la presentación real
- Ten un plan B (ratón físico)
- Configura sensibilidad media (1.0x - 1.5x)
- Usa el modo "Presentación" si está disponible

---

## 📞 Soporte

Si tienes problemas:

1. **Consulta la documentación**: [docs/](docs/)
2. **Reporta un bug**: [GitHub Issues](https://github.com/natone2/sensormouse/issues)
3. **Contacta**: [me@natone.pro](mailto:me@natone.pro)

---

**¡Disfruta usando SensorMouse! 🚀** 