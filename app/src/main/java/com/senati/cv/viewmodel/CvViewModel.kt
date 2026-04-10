// Paquete que ubica la clase en la capa de lógica de presentación (ViewModel)
package com.senati.cv.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Data Class que centraliza el estado de la UI (Single Source of Truth).
 * Representa "qué" debe mostrar la pantalla en un momento dado.
 */
data class CvUiState(
    val currentStep: RegistrationStep = RegistrationStep.PERSONAL_DATA,
    
    // Datos Personales (Form Fields)
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    
    // Errores de validación (Como el $errors de Laravel)
    val nameError: String? = null,
    val emailError: String? = null,
    val telefonoError: String? = null,
    val mostrarDialogo: Boolean = false // Controla la visibilidad de alertas/modales
)

/**
 * ViewModel encargado de gestionar la lógica del formulario de CV.
 * Sobrevive a cambios de configuración (como rotar la pantalla).
 */
class CvViewModel : ViewModel() {

    // _uiState: Estado interno mutable (privado). Solo el ViewModel puede modificarlo.
    private val _uiState = MutableStateFlow(CvUiState())

    // uiState: Estado expuesto como flujo de solo lectura para la UI (Compose).
    // Usamos asStateFlow() para evitar que la View pueda modificar el estado directamente.
    val uiState: StateFlow<CvUiState> = _uiState.asStateFlow()

    /**
     * Actualiza el nombre en el estado. 
     * Al usar .update { it.copy(...) }, aseguramos la inmutabilidad y seguridad entre hilos.
     */
    fun onNombreChange(nuevoNombre: String) {
        _uiState.update { it.copy(nombre = nuevoNombre, nombreError = null) }
    }

    /**
     * Actualiza el email y limpia el mensaje de error mientras el usuario escribe.
     */
    fun onEmailChange(nuevoEmail: String) {
        _uiState.update { it.copy(email = nuevoEmail, emailError = null) }
    }

    /**
     * Actualiza el teléfono aplicando la misma lógica de limpieza de errores.
     */
    fun onTelefonoChange(nuevoTelefono: String) {
        _uiState.update { it.copy(telefono = nuevoTelefono, telefonoError = null) }
    }

    /**
     * Cambia el estado del booleano para mostrar u ocultar el diálogo de éxito/confirmación.
     */
    fun mostrarConfirmacion(mostrar: Boolean) {
        _uiState.update { it.copy(mostrarDialogo = mostrar) }
    }

    /**
     * Lógica de validación de negocio.
     * Evalúa el estado actual y actualiza los campos de error si es necesario.
     * @return true si todos los datos cumplen los criterios.
     */
    fun onNextClicked() {
        if (validatePersonalData()) {
            setShowDialog(true)
        }
    }

        // Validación de campos vacíos
        val errorNombre = if (estadoActual.nombre.isBlank()) "El nombre es obligatorio" else null

        // Uso de Patterns de Android para validar formato de correo
        val errorEmail = if (!android.util.Patterns.EMAIL_ADDRESS.matcher(estadoActual.email).matches()) "Email inválido" else null

        // Validación de longitud mínima para teléfonos (ej. formato peruano)
        val errorTelefono = if (estadoActual.telefono.length < 9) "Teléfono debe tener al menos 9 dígitos" else null

        // Si cualquier error existe, la validación general falla
        if (errorNombre != null || errorEmail != null || errorTelefono != null) {
            esValido = false
        }
        val phoneErr = if (current.phone.length < 9) "Mínimo 9 dígitos" else null

        // Se "dispara" la actualización del estado con todos los errores encontrados
        _uiState.update {
            it.copy(
                nameError = nameErr,
                emailError = emailErr,
                phoneError = phoneErr
            )
        }

        return nameErr == null && emailErr == null && phoneErr == null
    }
}