package com.senati.cv.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Representa los diferentes pasos del registro de CV.
 * En web, esto sería similar a las rutas de un formulario multi-step.
 */
enum class RegistrationStep {
    PERSONAL_DATA,
    EDUCATION,
    EXPERIENCE,
    SUMMARY
}

/**
 * UiState: El ÚNICO objeto que define qué se muestra en pantalla.
 * Es como el "ViewModel" o "Data object" que pasarías a una vista en Laravel.
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
    val phoneError: String? = null,
    
    // Estado de la UI
    val showConfirmDialog: Boolean = false,
    val isRegistrationComplete: Boolean = false
)

/**
 * ViewModel: El "Cerebro". 
 * Actúa como un Controller que mantiene el estado (como una sesión o un Store).
 */
class CvViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CvUiState())
    val uiState: StateFlow<CvUiState> = _uiState.asStateFlow()

    // --- Actions (Handlers) ---

    fun onNameChange(newName: String) {
        _uiState.update { it.copy(name = newName, nameError = null) }
    }

    fun onEmailChange(newEmail: String) {
        _uiState.update { it.copy(email = newEmail, emailError = null) }
    }

    fun onPhoneChange(newPhone: String) {
        _uiState.update { it.copy(phone = newPhone, phoneError = null) }
    }

    fun setShowDialog(show: Boolean) {
        _uiState.update { it.copy(showConfirmDialog = show) }
    }

    /**
     * Lógica del botón "Siguiente".
     * Primero valida, si es correcto, pide confirmación.
     */
    fun onNextClicked() {
        if (validatePersonalData()) {
            setShowDialog(true)
        }
    }

    /**
     * Confirmación final del paso actual.
     * Mueve el estado al siguiente paso.
     */
    fun confirmAndContinue() {
        _uiState.update { state ->
            when (state.currentStep) {
                RegistrationStep.PERSONAL_DATA -> state.copy(
                    currentStep = RegistrationStep.EDUCATION,
                    showConfirmDialog = false
                )
                else -> state.copy(showConfirmDialog = false)
            }
        }
    }

    /**
     * Validación de los campos.
     * Similar a un FormRequest en Laravel.
     */
    private fun validatePersonalData(): Boolean {
        val current = _uiState.value
        
        val nameErr = if (current.name.isBlank()) "El nombre es requerido" else null
        val emailErr = when {
            current.email.isBlank() -> "El email es requerido"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(current.email).matches() -> "Email inválido"
            else -> null
        }
        val phoneErr = if (current.phone.length < 9) "Mínimo 9 dígitos" else null

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
