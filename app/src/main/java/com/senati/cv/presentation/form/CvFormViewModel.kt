package com.senati.cv.presentation.form

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.senati.cv.data.local.entities.AcademicInfoEntity
import com.senati.cv.domain.validators.CvValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * Estado que maneja la UI del formulario de CV.
 */
data class CvFormState(
    val currentStep: Int = 1,
    // Paso 1: Datos Personales
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val address: String = "",
    val photo: ByteArray? = null,
    // Errores de validación
    val nameError: String? = null,
    val emailError: String? = null,
    val phoneError: String? = null,
    val addressError: String? = null,
    
    // Paso 2: Info Académica (Campos temporales para añadir a la lista)
    val tempUniversity: String = "",
    val tempCareer: String = "",
    val tempYear: String = "",
    val isSuccess: Boolean = false
)

/**
 * ViewModel que gestiona exclusivamente el estado de los pasos y la validación.
 * Siguiendo el requerimiento, NO contiene lógica de base de datos.
 */
@HiltViewModel
class CvFormViewModel @Inject constructor(
    private val validator: CvValidator
) : ViewModel() {

    private val _uiState = MutableStateFlow(CvFormState())
    val uiState: StateFlow<CvFormState> = _uiState.asStateFlow()

    // Lista de registros académicos (usamos mutableStateListOf para facilitar la UI en Compose)
    val academicList = mutableStateListOf<AcademicInfoEntity>()

    // -- Funciones para actualizar campos --

    fun onNameChange(newValue: String) {
        _uiState.update { it.copy(name = newValue, nameError = null) }
    }

    fun onEmailChange(newValue: String) {
        _uiState.update { it.copy(email = newValue, emailError = null) }
    }

    fun onPhoneChange(newValue: String) {
        _uiState.update { it.copy(phone = newValue, phoneError = null) }
    }

    fun onAddressChange(newValue: String) {
        _uiState.update { it.copy(address = newValue, addressError = null) }
    }

    fun onPhotoChange(bytes: ByteArray?) {
        _uiState.update { it.copy(photo = bytes) }
    }

    fun onTempUniversityChange(newValue: String) {
        _uiState.update { it.copy(tempUniversity = newValue) }
    }

    fun onTempCareerChange(newValue: String) {
        _uiState.update { it.copy(tempCareer = newValue) }
    }

    fun onTempYearChange(newValue: String) {
        _uiState.update { it.copy(tempYear = newValue) }
    }

    // -- Gestión de Pasos y Validación --

    fun nextStep() {
        if (validateStep1()) {
            _uiState.update { it.copy(currentStep = 2) }
        }
    }

    fun previousStep() {
        _uiState.update { it.copy(currentStep = 1) }
    }

    private fun validateStep1(): Boolean {
        val state = _uiState.value
        val isNameValid = validator.isNotEmpty(state.name)
        val isEmailValid = validator.isValidEmail(state.email)
        val isPhoneValid = validator.isValidPhone(state.phone)
        val isAddressValid = validator.isNotEmpty(state.address)

        _uiState.update {
            it.copy(
                nameError = if (isNameValid) null else "Nombre requerido",
                emailError = if (isEmailValid) null else "Email inválido",
                phoneError = if (isPhoneValid) null else "Teléfono inválido (mín 9 dígitos)",
                addressError = if (isAddressValid) null else "Dirección requerida"
            )
        }

        return isNameValid && isEmailValid && isPhoneValid && isAddressValid
    }

    fun addAcademicInfo() {
        val state = _uiState.value
        if (validator.isNotEmpty(state.tempUniversity) && 
            validator.isNotEmpty(state.tempCareer) && 
            validator.isValidYear(state.tempYear)) {
            
            val newItem = AcademicInfoEntity(
                personalInfoId = 0, // Se asignará al guardar en la BD
                university = state.tempUniversity,
                career = state.tempCareer,
                year = state.tempYear
            )
            academicList.add(newItem)
            
            // Limpiar campos temporales
            _uiState.update { 
                it.copy(tempUniversity = "", tempCareer = "", tempYear = "") 
            }
        }
    }

    fun removeAcademicInfo(item: AcademicInfoEntity) {
        academicList.remove(item)
    }
}
