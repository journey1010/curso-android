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
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val nameError: String? = null,
    val emailError: String? = null,
    val phoneError: String? = null,
    val showDialog: Boolean = false
)

class CvViewModel : ViewModel() {

    // Estado interno (mutable) y estado público (solo lectura) siguiendo la arquitectura de Google
    private val _uiState = MutableStateFlow(CvUiState())
    val uiState: StateFlow<CvUiState> = _uiState.asStateFlow()

    // Funciones para actualizar el estado cuando el usuario escribe
    fun onNameChange(newName: String) {
        _uiState.update { it.copy(name = newName, nameError = null) }
    }

    fun onEmailChange(newEmail: String) {
        _uiState.update { it.copy(email = newEmail, emailError = null) }
    }

    fun onPhoneChange(newPhone: String) {
        _uiState.update { it.copy(phone = newPhone, phoneError = null) }
    }

    // Control del diálogo de confirmación
    fun showDialog(show: Boolean) {
        _uiState.update { it.copy(showDialog = show) }
    }

    /**
     * Valida los datos ingresados. Retorna true si todo es correcto.
     */
    fun validateData(): Boolean {
        val stateCurrent = _uiState.value
        var isValid = true

        val errorName = if (stateCurrent.name.isBlank()) "El nombre es obligatorio" else null
        val errorEmail = if (!android.util.Patterns.EMAIL_ADDRESS.matcher(stateCurrent.email).matches()) "Email inválido" else null
        val errorPhone = if (stateCurrent.phone.length < 9) "Teléfono debe tener al menos 9 dígitos" else null

        if (errorName != null || errorEmail != null || errorName != null) {
            isValid = false
        }

        _uiState.update {
            it.copy(
                nameError = errorName,
                emailError = errorEmail,
                phoneError = errorPhone
            )
        }
        return isValid
    }
}
