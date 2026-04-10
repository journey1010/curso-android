package com.senati.cv.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Representa los diferentes pasos del registro de CV.
 * Similar a las fases de un formulario multi-step en web.
 */
enum class RegistrationStep {
    PERSONAL_DATA,
    EDUCATION,
    EXPERIENCE,
    SUMMARY
}

/**
 * Estado único de la UI. Sigue el patrón Single Source of Truth.
 */
data class CvUiState(
    val currentStep: RegistrationStep = RegistrationStep.PERSONAL_DATA,
    
    // Datos del formulario
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    
    // Errores (como el $errors de Laravel)
    val nameError: String? = null,
    val emailError: String? = null,
    val phoneError: String? = null,
    
    // Control de UI
    val showConfirmDialog: Boolean = false
)

class CvViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CvUiState())
    val uiState: StateFlow<CvUiState> = _uiState.asStateFlow()

    // --- Handlers de entrada (Input Handlers) ---

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
     * Lógica al presionar "Siguiente". 
     * Valida y si es correcto, dispara el modal de confirmación.
     */
    fun onNextClicked() {
        if (validatePersonalData()) {
            setShowDialog(true)
        }
    }

    /**
     * Ejecuta la transición al siguiente paso. 
     * En web sería como un redirect a la siguiente ruta del form.
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
     * Validador de datos. Actúa como un FormRequest.
     */
    private fun validatePersonalData(): Boolean {
        val current = _uiState.value
        
        val nameErr = if (current.name.isBlank()) "El nombre es requerido" else null
        val emailErr = when {
            current.email.isBlank() -> "El email es requerido"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(current.email).matches() -> "Formato inválido"
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
