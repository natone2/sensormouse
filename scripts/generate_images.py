#!/usr/bin/env python3
"""
Script para generar imágenes de SensorMouse usando prompts optimizados.
Este script crea las imágenes más importantes para la documentación.
"""

import os
import sys
from pathlib import Path

def create_image_prompt(image_type, filename):
    """Crea un prompt optimizado para generar una imagen específica."""
    
    prompts = {
        "main_screen_disconnected": """
Crea una captura de pantalla realista de una app Android llamada "SensorMouse" en estado desconectado.

La pantalla debe mostrar:
- Título "SensorMouse" en la parte superior con icono de ratón
- Un icono grande de ratón en el centro
- Estado: "Desconectado" con icono rojo ❌
- Campo de texto para IP del servidor: "192.168.1.100"
- Campo de texto para puerto: "5000"
- Botón azul "Conectar"
- Slider de sensibilidad en 1.0x
- Botón "Calibrar"
- Texto "Sesiones restantes: 30 días"
- Botón "Actualizar a Pro"
- Tres iconos de botones de ratón en la parte inferior (izquierdo, derecho, central)

Estilo: Material Design, colores azul (#2196F3) y blanco, interfaz moderna y limpia.
Formato: 1080x1920px, PNG, alta calidad.
""",
        
        "main_screen_connected": """
Crea una captura de pantalla realista de la app Android "SensorMouse" en estado conectado.

La pantalla debe mostrar:
- Título "SensorMouse" en la parte superior
- Icono de ratón en el centro
- Estado: "Conectado" con icono verde ✅
- Texto "Servidor: 192.168.1.100:5000"
- Botón "Desconectar"
- Slider de sensibilidad en 1.0x
- Botón "Calibrar"
- Texto "Sesiones restantes: 30 días"
- Botón "Actualizar a Pro"
- Tres iconos de botones de ratón en la parte inferior

Estilo: Material Design, colores azul (#2196F3) y verde (#4CAF50), interfaz moderna.
Formato: 1080x1920px, PNG, alta calidad.
""",
        
        "terminal_connected": """
Crea una captura de pantalla de una terminal/consola mostrando el servidor SensorMouse con un cliente conectado.

La terminal debe mostrar:
- Fondo negro (#000000) con texto verde (#00FF00)
- Prompt: "$ python main.py"
- Salida del servidor iniciando:
  * "🚀 Servidor SensorMouse iniciado"
  * "📡 Escuchando en puerto 5000"
  * "🌐 IP del servidor: 192.168.1.100"
  * "✅ Listo para conexiones"
  * "⏳ Esperando dispositivos..."
- Nuevas líneas con actividad:
  * "📱 Dispositivo conectado: 192.168.1.50"
  * "🖱️ Movimiento detectado: x=15, y=-8"
  * "🖱️ Clic izquierdo"
  * "🖱️ Movimiento detectado: x=3, y=12"
  * "🖱️ Clic derecho"

Estilo: Terminal realista, fuente monoespaciada, emojis coloridos, cursor parpadeando.
Formato: 1920x1080px, PNG, alta calidad.
""",
        
        "installation_flow": """
Crea un diagrama visual profesional del flujo de instalación de SensorMouse.

El diagrama debe mostrar:
- 6 pasos conectados con flechas azules:
  1. 📥 "Descargar Proyecto" (icono de descarga)
  2. 📦 "Instalar Dependencias" (icono de paquete)
  3. 🖥️ "Configurar Servidor" (icono de servidor)
  4. 📱 "Instalar App Android" (icono de móvil)
  5. 🔗 "Conectar Dispositivos" (icono de conexión WiFi)
  6. 🖱️ "Usar como Ratón" (icono de ratón)

Características:
- Cajas rectangulares con bordes redondeados
- Iconos grandes y claros en cada paso
- Flechas azules (#2196F3) conectando los pasos
- Fondo blanco con sombras sutiles
- Texto en fuente moderna y legible

Estilo: Diagrama de flujo profesional, diseño limpio y moderno.
Formato: 1920x1080px, PNG, alta calidad.
""",
        
        "presentations": """
Crea una imagen que muestre el uso de SensorMouse para presentaciones profesionales.

La imagen debe mostrar:
- Persona de negocios sosteniendo un smartphone Android
- Flecha azul apuntando hacia una pantalla/proyector grande
- Presentación de PowerPoint/Keynote en la pantalla con gráficos y texto
- Texto destacado: "Controla presentaciones desde el móvil"
- Subtítulo: "Sensibilidad recomendada: 1.5x - 2.0x"
- Iconos pequeños: ratón, smartphone, pantalla

Elementos visuales:
- Ambiente de oficina/sala de conferencias
- Colores corporativos (azul #2196F3, blanco, gris)
- Iluminación profesional
- Diseño limpio y moderno
- Persona vestida formalmente

Estilo: Ilustración profesional, colores corporativos, diseño limpio y elegante.
Formato: 1920x1080px, PNG, alta calidad.
""",
        
        "version_comparison": """
Crea una infografía moderna comparando las versiones gratuita y Pro de SensorMouse.

La infografía debe mostrar:

LADO IZQUIERDO - "Versión Gratuita":
- Icono de regalo/checkmark
- Características con checkmarks:
  * ✅ Control completo del ratón
  * ✅ Calibración básica
  * ✅ Sensibilidad estándar (1.0x)
  * ✅ 30 días de prueba
- Precio destacado: "GRATIS"
- Botón "Probar"

LADO DERECHO - "Versión Pro":
- Icono de estrella/corona
- Características con checkmarks:
  * ✅ Uso ilimitado
  * ✅ Sensibilidad avanzada (0.1x - 5.0x)
  * ✅ Calibración avanzada
  * ✅ Múltiples perfiles
  * ✅ Sin publicidad
  * ✅ Temas personalizados
  * ✅ Estadísticas de uso
- Precio destacado: "$2.99"
- Botón "Comprar Pro" (dorado)

Diseño:
- Dos columnas claramente separadas
- Colores: azul (#2196F3) para gratis, dorado (#FFD700) para Pro
- Iconos Material Design
- Tipografía moderna y legible
- Fondo blanco con sombras sutiles

Estilo: Infografía moderna, diseño atractivo, colores contrastantes.
Formato: 1920x1080px, PNG, alta calidad.
"""
    }
    
    return prompts.get(image_type, "Prompt no encontrado")

def main():
    """Función principal del script."""
    
    print("🎨 Generador de Imágenes para SensorMouse")
    print("=" * 50)
    
    # Definir las imágenes prioritarias
    priority_images = [
        ("app", "main_screen_disconnected"),
        ("app", "main_screen_connected"),
        ("server", "terminal_connected"),
        ("diagrams", "installation_flow"),
        ("use_cases", "presentations"),
        ("infographics", "version_comparison")
    ]
    
    print("\n📋 Imágenes a generar:")
    for i, (folder, image) in enumerate(priority_images, 1):
        print(f"{i}. {folder}/{image}")
    
    print("\n🚀 Instrucciones para generar:")
    print("1. Ve a https://chat.openai.com")
    print("2. Asegúrate de tener acceso a GPT-4o")
    print("3. Copia y pega cada prompt en el chat")
    print("4. Descarga las imágenes generadas")
    print("5. Guárdalas en la carpeta correspondiente")
    
    print("\n📁 Estructura de carpetas:")
    print("docs/images/")
    for folder, image in priority_images:
        print(f"  ├── {folder}/")
        print(f"  │   └── {image}.png")
    
    print("\n🎯 Prompts optimizados:")
    print("=" * 50)
    
    for folder, image in priority_images:
        print(f"\n📱 {folder}/{image}:")
        print("-" * 30)
        prompt = create_image_prompt(image, f"{image}.png")
        print(prompt)
        print("\n" + "="*50)

if __name__ == "__main__":
    main() 