# Guía de Contribución - SensorMouse

¡Gracias por tu interés en contribuir a SensorMouse! Este documento te guiará a través del proceso de contribución.

## Tabla de Contenidos

- [Código de Conducta](#código-de-conducta)
- [¿Cómo Puedo Contribuir?](#cómo-puedo-contribuir)
- [Configuración del Entorno de Desarrollo](#configuración-del-entorno-de-desarrollo)
- [Flujo de Trabajo](#flujo-de-trabajo)
- [Estándares de Código](#estándares-de-código)
- [Testing](#testing)
- [Documentación](#documentación)
- [Reportar Bugs](#reportar-bugs)
- [Solicitar Features](#solicitar-features)
- [Pull Requests](#pull-requests)
- [Releases](#releases)

## Código de Conducta

### Nuestro Compromiso

En el interés de fomentar un ambiente abierto y acogedor, nosotros como contribuyentes y mantenedores nos comprometemos a hacer de la participación en nuestro proyecto y nuestra comunidad una experiencia libre de acoso para todos, independientemente de la edad, tamaño corporal, discapacidad, etnia, características sexuales, identidad y expresión de género, nivel de experiencia, educación, estatus socioeconómico, nacionalidad, apariencia personal, raza, religión, o identidad y orientación sexual.

### Nuestros Estándares

Ejemplos de comportamiento que contribuyen a crear un ambiente positivo:

- Usar lenguaje acogedor e inclusivo
- Respetar diferentes puntos de vista y experiencias
- Aceptar graciosamente la crítica constructiva
- Enfocarse en lo que es mejor para la comunidad
- Mostrar empatía hacia otros miembros de la comunidad

Ejemplos de comportamiento inaceptable:

- El uso de lenguaje o imágenes sexualizadas
- Trolling, comentarios insultantes/despectivos, y ataques personales o políticos
- Acoso público o privado
- Publicar información privada de otros, como direcciones físicas o electrónicas, sin permiso explícito
- Otra conducta que razonablemente podría considerarse inapropiada en un entorno profesional

## ¿Cómo Puedo Contribuir?

### Tipos de Contribuciones

#### Reportar Bugs
- Usa el template de bug report
- Incluye información detallada del sistema
- Proporciona pasos para reproducir

#### Solicitar Features
- Describe la funcionalidad deseada
- Explica el caso de uso
- Considera la implementación

#### Mejorar Documentación
- Corregir errores gramaticales
- Añadir ejemplos de código
- Mejorar claridad

#### Contribuir Código
- Implementar nuevas funcionalidades
- Corregir bugs existentes
- Optimizar rendimiento
- Mejorar tests

### Áreas de Contribución

#### Frontend (Android)
- **Lenguaje**: Kotlin
- **Arquitectura**: MVVM con LiveData
- **UI/UX**: Material Design
- **Testing**: Unit tests, UI tests

#### Backend (Python)
- **Lenguaje**: Python 3.8+
- **Framework**: Socket TCP/IP nativo
- **Testing**: pytest
- **Documentación**: docstrings

#### Web
- **Frontend**: HTML5, CSS3, JavaScript
- **Documentación**: Markdown
- **Diseño**: Responsive design

## Configuración del Entorno de Desarrollo

### Prerrequisitos

```bash
# Herramientas básicas
git >= 2.30
python >= 3.8
node >= 16.0
java >= 11
android-sdk

# IDEs recomendados
# - Android Studio (para desarrollo Android)
# - VS Code (para Python y web)
# - PyCharm (para Python)
```

### Configuración Inicial

```bash
# 1. Fork del repositorio
git clone https://github.com/natone2/sensormouse.git
cd sensormouse

# 2. Configurar entorno Python
python -m venv venv
source venv/bin/activate  # Linux/macOS
venv\Scripts\activate     # Windows
pip install -r server/requirements.txt
pip install -r requirements-dev.txt

# 3. Configurar Android
cd android-app
./gradlew build

# 4. Configurar pre-commit hooks
pre-commit install
```

### Estructura del Proyecto

```
sensormouse/
├── android-app/          # Aplicación Android
│   ├── app/
│   ├── build.gradle
│   └── gradle/
├── server/               # Servidor Python
│   ├── main.py
│   ├── utils.py
│   └── requirements.txt
├── website/              # Sitio web
│   ├── index.html
│   ├── assets/
│   └── docs/
├── docs/                 # Documentación
├── tests/                # Tests
└── scripts/              # Scripts de utilidad
```

## Flujo de Trabajo

### 1. Crear una Issue

Antes de comenzar a trabajar:

1. Verifica que no exista una issue similar
2. Crea una nueva issue con el template apropiado
3. Describe claramente el problema o feature
4. Asigna etiquetas apropiadas

### 2. Crear una Rama

```bash
# Crear rama desde main
git checkout main
git pull origin main
git checkout -b feature/nombre-de-la-feature

# O para bugs
git checkout -b fix/nombre-del-bug
```

### 3. Desarrollar

```bash
# Hacer cambios
# Ejecutar tests
python -m pytest tests/
./gradlew test  # Android

# Verificar calidad del código
flake8 server/
black server/
isort server/
```

### 4. Commit

```bash
# Usar mensajes de commit convencionales
git commit -m "feat: añadir soporte para magnetómetro"
git commit -m "fix: corregir latencia alta en conexión"
git commit -m "docs: actualizar guía de instalación"
```

### 5. Push y Pull Request

```bash
git push origin feature/nombre-de-la-feature
# Crear Pull Request en GitHub
```

## Estándares de Código

### Python

#### Formato
```python
# Usar Black para formateo
black server/

# Usar isort para imports
isort server/

# Usar flake8 para linting
flake8 server/
```

#### Convenciones
```python
# Nombres de variables y funciones
variable_name = "snake_case"
function_name = "snake_case"

# Nombres de clases
class ClassName:
    pass

# Constantes
CONSTANT_NAME = "UPPER_CASE"

# Docstrings
def process_sensor_data(data: dict) -> dict:
    """
    Procesa datos de sensores y aplica filtros.
    
    Args:
        data: Diccionario con datos de sensores
        
    Returns:
        Diccionario con datos procesados
        
    Raises:
        ValueError: Si los datos son inválidos
    """
    pass
```

### Kotlin (Android)

#### Formato
```kotlin
// Usar ktlint
./gradlew ktlintFormat

// Usar detekt para análisis estático
./gradlew detekt
```

#### Convenciones
```kotlin
// Nombres de variables y funciones
val variableName = "camelCase"
fun functionName() = "camelCase"

// Nombres de clases
class ClassName

// Constantes
companion object {
    const val CONSTANT_NAME = "UPPER_CASE"
}

// Documentación
/**
 * Procesa datos de sensores y aplica filtros.
 *
 * @param data Datos de sensores a procesar
 * @return Datos procesados
 * @throws IllegalArgumentException si los datos son inválidos
 */
fun processSensorData(data: SensorData): ProcessedData {
    // Implementación
}
```

### JavaScript/HTML/CSS

#### Formato
```bash
# Usar Prettier
npx prettier --write website/

# Usar ESLint
npx eslint website/
```

#### Convenciones
```javascript
// Nombres de variables y funciones
const variableName = "camelCase";
function functionName() {
    return "camelCase";
}

// Nombres de clases
class ClassName {
    constructor() {
        // Constructor
    }
}

// Documentación JSDoc
/**
 * Procesa datos de sensores
 * @param {Object} data - Datos de sensores
 * @returns {Object} Datos procesados
 */
function processSensorData(data) {
    // Implementación
}
```

## Testing

### Python

```python
# Estructura de tests
tests/
├── unit/
│   ├── test_sensor_processor.py
│   └── test_network.py
├── integration/
│   └── test_client_server.py
└── conftest.py

# Ejecutar tests
python -m pytest tests/
python -m pytest tests/ -v --cov=server
```

### Android

```kotlin
// Estructura de tests
android-app/app/src/
├── test/           # Unit tests
└── androidTest/    # Instrumented tests

// Ejecutar tests
./gradlew test
./gradlew connectedAndroidTest
```

### Web

```javascript
// Tests con Jest
npm test
npm run test:coverage
```

## Documentación

### Estándares de Documentación

#### README
- Descripción clara del proyecto
- Instrucciones de instalación
- Ejemplos de uso
- Información de contribución

#### Docstrings (Python)
```python
def complex_function(param1: str, param2: int) -> bool:
    """
    Descripción breve de la función.
    
    Descripción más detallada si es necesaria.
    
    Args:
        param1: Descripción del primer parámetro
        param2: Descripción del segundo parámetro
        
    Returns:
        Descripción del valor de retorno
        
    Raises:
        ValueError: Cuando algo va mal
        
    Example:
        >>> complex_function("test", 42)
        True
    """
    pass
```

#### KDoc (Kotlin)
```kotlin
/**
 * Descripción de la función.
 *
 * @param param1 Descripción del primer parámetro
 * @param param2 Descripción del segundo parámetro
 * @return Descripción del valor de retorno
 * @throws IllegalArgumentException cuando algo va mal
 */
fun complexFunction(param1: String, param2: Int): Boolean {
    // Implementación
}
```

### Actualizar Documentación

Cuando hagas cambios que afecten la documentación:

1. Actualiza docstrings/comentarios
2. Actualiza README si es necesario
3. Actualiza guías de usuario
4. Actualiza changelog

## Reportar Bugs

### Template de Bug Report

```markdown
**Descripción del Bug**
Descripción clara y concisa del bug.

**Pasos para Reproducir**
1. Ve a '...'
2. Haz clic en '...'
3. Desplázate hacia abajo hasta '...'
4. Ve el error

**Comportamiento Esperado**
Descripción de lo que debería suceder.

**Comportamiento Actual**
Descripción de lo que realmente sucede.

**Capturas de Pantalla**
Si es aplicable, añade capturas de pantalla.

**Información del Sistema**
- OS: [ej. Windows 10, macOS 11, Ubuntu 20.04]
- Python: [ej. 3.9.0]
- Android: [ej. 11, API 30]
- Dispositivo: [ej. Samsung Galaxy S21]

**Logs**
Incluye logs relevantes.

**Contexto Adicional**
Cualquier otra información sobre el problema.
```

## Solicitar Features

### Template de Feature Request

```markdown
**Problema que Resuelve**
Descripción clara del problema que esta feature resolvería.

**Solución Propuesta**
Descripción de la solución que te gustaría ver.

**Alternativas Consideradas**
Descripción de cualquier solución alternativa que hayas considerado.

**Contexto Adicional**
Cualquier contexto adicional, capturas de pantalla, etc.
```

## Pull Requests

### Checklist de PR

- [ ] He leído y sigo las guías de contribución
- [ ] Mi código sigue los estándares del proyecto
- [ ] He añadido tests para mi código
- [ ] He actualizado la documentación según sea necesario
- [ ] Mi PR no introduce nuevos warnings
- [ ] He añadido una descripción clara de los cambios
- [ ] He vinculado la issue relacionada

### Template de PR

```markdown
**Descripción**
Descripción clara de los cambios realizados.

**Tipo de Cambio**
- [ ] Bug fix
- [ ] Nueva feature
- [ ] Breaking change
- [ ] Documentación

**Testing**
- [ ] Tests unitarios pasan
- [ ] Tests de integración pasan
- [ ] Tests manuales realizados

**Screenshots**
Si es aplicable, añade capturas de pantalla.

**Checklist**
- [ ] Mi código sigue los estándares del proyecto
- [ ] He añadido tests para mi código
- [ ] He actualizado la documentación
- [ ] Mi PR no introduce nuevos warnings
```

## Releases

### Proceso de Release

1. **Preparación**
   - Actualizar versiones en archivos de configuración
   - Actualizar changelog
   - Crear rama de release

2. **Testing**
   - Ejecutar suite completa de tests
   - Testing manual en diferentes plataformas
   - Verificar documentación

3. **Release**
   - Crear tag de versión
   - Generar APK de release
   - Publicar en GitHub Releases
   - Actualizar documentación

### Versionado

Seguimos [Semantic Versioning](https://semver.org/):

- **MAJOR**: Cambios incompatibles
- **MINOR**: Nuevas funcionalidades compatibles
- **PATCH**: Correcciones de bugs compatibles

## Reconocimiento

### Contribuidores

Los contribuidores serán reconocidos en:

- README del proyecto
- Página de contribuidores
- Changelog
- Releases de GitHub

### Criterios de Contribuidor

Para ser listado como contribuidor:

- Al menos una contribución significativa
- Seguir las guías de contribución
- Participar constructivamente en la comunidad

## Contacto

### Canales de Comunicación

- **GitHub Issues**: Para bugs y features
- **GitHub Discussions**: Para preguntas generales
- **Discord**: Para comunicación en tiempo real
- **Email**: dev@sensormouse.com para asuntos privados

### Mantenedores

- **Alex M** (@alexm) - Líder del proyecto
- **Equipo de Desarrollo** - Revisores de PR

---

**¡Gracias por contribuir a SensorMouse!** Tu trabajo ayuda a hacer este proyecto mejor para todos. 