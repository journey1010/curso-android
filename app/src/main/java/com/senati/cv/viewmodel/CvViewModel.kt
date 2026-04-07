package com.senati.cv.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Representa el estado de la interfaz de usuario para el registro de CV.
 */
data class CvUiState(
    val nombre: String = "",
    val email: String = "",
    val telefono: String = "",
    val nombreError: String? = null,
    val emailError: String? = null,
    val telefonoError: String? = null,
    val mostrarDialogo: Boolean = false
)

class CvViewModel : ViewModel() {

    // Estado interno (mutable) y estado público (solo lectura) siguiendo la arquitectura de Google
    private val _uiState = MutableStateFlow(CvUiState())
    val uiState: StateFlow<CvUiState> = _uiState.asStateFlow()

    // Funciones para actualizar el estado cuando el usuario escribe
    fun onNombreChange(nuevoNombre: String) {
        _uiState.update { it.copy(nombre = nuevoNombre, nombreError = null) }
    }

    fun onEmailChange(nuevoEmail: String) {
        _uiState.update { it.copy(email = nuevoEmail, emailError = null) }
    }

    fun onTelefonoChange(nuevoTelefono: String) {
        _uiState.update { it.copy(telefono = nuevoTelefono, telefonoError = null) }
    }

    // Control del diálogo de confirmación
    fun mostrarConfirmacion(mostrar: Boolean) {
        _uiState.update { it.copy(mostrarDialogo = mostrar) }
    }

    /**
     * Valida los datos ingresados. Retorna true si todo es correcto.
     */
    fun validarDatos(): Boolean {
        val estadoActual = _uiState.value
        var esValido = true

        val errorNombre = if (estadoActual.nombre.isBlank()) "El nombre es obligatorio" else null
        val errorEmail = if (!android.util.Patterns.EMAIL_ADDRESS.matcher(estadoActual.email).matches()) "Email inválido" else null
        val errorTelefono = if (estadoActual.telefono.length < 9) "Teléfono debe tener al menos 9 dígitos" else null

        if (errorNombre != null || errorEmail != null || errorTelefono != null) {
            esValido = false
        }

        _uiState.update {
            it.copy(
                nombreError = errorNombre,
                emailError = errorEmail,
                telefonoError = errorTelefono
            )
        }
        return esValido
    }
}
