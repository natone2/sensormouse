# 🤝 Guía de Contribución

¡Gracias por tu interés en contribuir a SensorMouse! Este documento te ayudará a empezar.

## 📋 Tabla de Contenidos

- [¿Cómo puedo contribuir?](#cómo-puedo-contribuir)
- [Configuración del entorno](#configuración-del-entorno)
- [Proceso de contribución](#proceso-de-contribución)
- [Estándares de código](#estándares-de-código)
- [Reportar bugs](#reportar-bugs)
- [Solicitar features](#solicitar-features)

## 🎯 ¿Cómo puedo contribuir?

### Tipos de contribuciones que necesitamos:

- 🐛 **Corrección de bugs**
- ✨ **Nuevas funcionalidades**
- 📚 **Mejoras en documentación**
- 🌍 **Traducciones**
- 🎨 **Mejoras de UI/UX**
- ⚡ **Optimizaciones de rendimiento**
- 🧪 **Tests**

### Áreas específicas que necesitan ayuda:

- **Android App**: Mejoras en la interfaz, nuevas funcionalidades
- **Servidor Python**: Optimizaciones, nuevas características
- **Documentación**: Guías de usuario, documentación técnica
- **Testing**: Tests unitarios, tests de integración

## 🛠️ Configuración del entorno

### Prerrequisitos

- Android Studio (última versión)
- Python 3.8+
- Git

### Configuración del proyecto

```bash
# 1. Fork el repositorio
# 2. Clona tu fork
git clone https://github.com/TU_USUARIO/sensormouse.git
cd sensormouse

# 3. Añade el repositorio original como upstream
git remote add upstream https://github.com/natone2/sensormouse.git

# 4. Configura el proyecto Android
cd android-app
# Abre en Android Studio y sincroniza

# 5. Configura el servidor Python
cd ../server
python -m venv venv
source venv/bin/activate  # Windows: venv\Scripts\activate
pip install -r requirements.txt
```

## 📝 Proceso de contribución

### 1. Crear una issue

Antes de empezar a codificar:
- Busca si ya existe una issue similar
- Crea una nueva issue describiendo tu propuesta
- Espera feedback del mantenedor

### 2. Fork y clona

```bash
git clone https://github.com/TU_USUARIO/sensormouse.git
cd sensormouse
```

### 3. Crear una rama

```bash
git checkout -b feature/nombre-de-tu-feature
# o
git checkout -b fix/nombre-del-bug
```

### 4. Hacer cambios

- Escribe código limpio y bien documentado
- Sigue los estándares de código
- Añade tests si es necesario
- Actualiza documentación si es relevante

### 5. Commit y push

```bash
git add .
git commit -m "feat: añadir nueva funcionalidad X"
git push origin feature/nombre-de-tu-feature
```

### 6. Crear Pull Request

- Ve a tu fork en GitHub
- Crea un Pull Request
- Describe claramente los cambios
- Referencia la issue relacionada

## 📏 Estándares de código

### Kotlin (Android)

- Usa **Kotlin** en lugar de Java
- Sigue las [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Usa **Kotlin Coroutines** para operaciones asíncronas
- Implementa **MVVM** o **Clean Architecture**
- Usa **ViewBinding** o **DataBinding**

### Python (Servidor)

- Sigue [PEP 8](https://www.python.org/dev/peps/pep-0008/)
- Usa **type hints** cuando sea posible
- Documenta funciones con **docstrings**
- Usa **async/await** para operaciones I/O

### Git

- Usa [Conventional Commits](https://www.conventionalcommits.org/):
  - `feat:` nueva funcionalidad
  - `fix:` corrección de bug
  - `docs:` cambios en documentación
  - `style:` cambios de formato
  - `refactor:` refactorización de código
  - `test:` añadir tests
  - `chore:` tareas de mantenimiento

### Ejemplos de commits:

```bash
git commit -m "feat: añadir calibración avanzada"
git commit -m "fix: corregir crash en MainActivity"
git commit -m "docs: actualizar README con nuevas instrucciones"
```

## 🐛 Reportar bugs

### Antes de reportar:

1. Busca en las issues existentes
2. Verifica que el bug no esté ya reportado
3. Intenta reproducir el bug

### Template para reportar bugs:

```markdown
**Descripción del bug**
Descripción clara y concisa del bug.

**Pasos para reproducir**
1. Ve a '...'
2. Haz clic en '...'
3. Desplázate hacia abajo hasta '...'
4. Ve el error

**Comportamiento esperado**
Descripción de lo que debería pasar.

**Comportamiento actual**
Descripción de lo que realmente pasa.

**Capturas de pantalla**
Si es aplicable, añade capturas de pantalla.

**Información del dispositivo**
- Dispositivo: [ej. iPhone 6]
- OS: [ej. iOS 8.1]
- Versión de la app: [ej. 1.0.0]

**Información adicional**
Cualquier otra información relevante.
```

## 💡 Solicitar features

### Template para solicitar features:

```markdown
**¿Tu solicitud de feature está relacionada con un problema?**
Descripción del problema.

**Describe la solución que te gustaría**
Descripción clara y concisa de lo que quieres que pase.

**Describe alternativas que has considerado**
Descripción clara y concisa de cualquier solución o feature alternativa que hayas considerado.

**Información adicional**
Añade cualquier otro contexto o capturas de pantalla sobre la solicitud de feature aquí.
```

## 🎉 Reconocimiento

Todas las contribuciones serán reconocidas en:

- El archivo [README.md](README.md)
- La sección de [Agradecimientos](README.md#agradecimientos)
- El [CHANGELOG.md](CHANGELOG.md)

## 📞 Contacto

Si tienes preguntas sobre cómo contribuir:

- Abre una [issue](https://github.com/natone2/sensormouse/issues)
- Contacta al mantenedor: [alex@example.com](mailto:alex@example.com)

---

**¡Gracias por contribuir a hacer SensorMouse mejor! 🚀** 