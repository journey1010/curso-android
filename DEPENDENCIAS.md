# Guía de Dependencias

Este proyecto utiliza librerías modernas del ecosistema Android para garantizar un código limpio, mantenible y eficiente.

---

### 1. Jetpack Compose (UI)
*   **Propósito**: Es el kit de herramientas moderno para construir interfaces de usuario nativas.
*   **Uso**: Define todas las pantallas de nuestra aplicación (`CvFormScreen`, `CvDetailsScreen`).
*   **Ventaja**: Menos código, desarrollo más rápido y estado reactivo.

### 2. Room (Base de Datos)
*   **Propósito**: Proporciona una capa de abstracción sobre SQLite para un acceso robusto a la base de datos.
*   **Uso**: Persiste la información personal y académica del usuario, incluyendo la conversión de imágenes a `ByteArray`.
*   **Ventaja**: Verificación de consultas en tiempo de compilación y soporte para Coroutines.

### 3. Hilt (Inyección de Dependencias)
*   **Propósito**: Simplifica la inyección de dependencias en Android.
*   **Uso**: Inyecta automáticamente el `CvValidator` en el `CvFormViewModel` y gestiona el ciclo de vida de los ViewModels.
*   **Ventaja**: Facilita las pruebas unitarias y reduce el código "boilerplate" de configuración.

### 4. Coroutines & Flow (Concurrencia)
*   **Propósito**: Maneja tareas asíncronas de forma sencilla.
*   **Uso**:
    *   **Coroutines**: Para insertar datos en la BD sin bloquear la interfaz.
    *   **Flow**: Para observar cambios en la base de datos en tiempo real (en `CvViewActivity`).
*   **Ventaja**: Código más legible y manejo eficiente de hilos.

### 5. KSP (Kotlin Symbol Processing)
*   **Propósito**: Herramienta de procesamiento de anotaciones.
*   **Uso**: Reemplaza a KAPT para generar el código necesario de Room y Hilt de forma más rápida.
*   **Ventaja**: Tiempos de compilación reducidos.

### 6. FileProvider
*   **Propósito**: Permite compartir archivos de forma segura entre aplicaciones.
*   **Uso**: Necesario para que la aplicación de la cámara pueda guardar la foto en una ubicación accesible para nuestra app.
