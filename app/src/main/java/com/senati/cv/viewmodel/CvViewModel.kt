package com.senati.cv.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.senati.cv.domain.model.AcademicLevel
import com.senati.cv.domain.model.Certificate
import com.senati.cv.domain.model.Education
import com.senati.cv.domain.model.MaritalStatus
import com.senati.cv.domain.model.PersonalData
import com.senati.cv.domain.model.RegistrationStep
import com.senati.cv.domain.repository.CvRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PersonalDataFormState(
    val dni: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val address: String = "",
    val maritalStatus: MaritalStatus = MaritalStatus.SINGLE,
    val photoUri: String = "",
    val phone: String = "",
    val email: String = "",
    val dniError: String? = null,
    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val addressError: String? = null,
    val phoneError: String? = null,
    val emailError: String? = null
)

data class EducationFormState(
    val universityName: String = "",
    val universityCareer: String = "",
    val universityStartDate: String = "",
    val universityEndDate: String = "",
    val stillAttending: Boolean = false,
    val schoolName: String = "",
    val maxAcademicLevel: AcademicLevel = AcademicLevel.SECONDARY,
    val universityNameError: String? = null,
    val schoolNameError: String? = null
)

data class CertificatesFormState(
    val savedCertificates: List<Certificate> = emptyList(),
    val currentName: String = "",
    val currentInstitution: String = "",
    val currentObtainedDate: String = "",
    val currentDescription: String = "",
    val nameError: String? = null,
    val institutionError: String? = null
)

data class CvUiState(
    val currentStep: RegistrationStep = RegistrationStep.PERSONAL_DATA,
    val personalData: PersonalDataFormState = PersonalDataFormState(),
    val education: EducationFormState = EducationFormState(),
    val certificates: CertificatesFormState = CertificatesFormState(),
    val showConfirmDialog: Boolean = false,
    val isSaving: Boolean = false,
    val saveSuccess: Boolean = false,
    val saveError: String? = null
)

class CvViewModel(private val repository: CvRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(CvUiState())
    val uiState: StateFlow<CvUiState> = _uiState.asStateFlow()

    // ── Personal Data handlers ───────────────────────────────────────────────

    fun onDniChange(value: String) =
        _uiState.update { it.copy(personalData = it.personalData.copy(dni = value, dniError = null)) }

    fun onFirstNameChange(value: String) =
        _uiState.update { it.copy(personalData = it.personalData.copy(firstName = value, firstNameError = null)) }

    fun onLastNameChange(value: String) =
        _uiState.update { it.copy(personalData = it.personalData.copy(lastName = value, lastNameError = null)) }

    fun onAddressChange(value: String) =
        _uiState.update { it.copy(personalData = it.personalData.copy(address = value, addressError = null)) }

    fun onMaritalStatusChange(value: MaritalStatus) =
        _uiState.update { it.copy(personalData = it.personalData.copy(maritalStatus = value)) }

    fun onPhotoUriChange(value: String) =
        _uiState.update { it.copy(personalData = it.personalData.copy(photoUri = value)) }

    fun onPhoneChange(value: String) =
        _uiState.update { it.copy(personalData = it.personalData.copy(phone = value, phoneError = null)) }

    fun onEmailChange(value: String) =
        _uiState.update { it.copy(personalData = it.personalData.copy(email = value, emailError = null)) }

    fun onNextFromPersonalData() {
        if (validatePersonalData()) setShowDialog(true)
    }

    private fun validatePersonalData(): Boolean {
        val pd = _uiState.value.personalData
        val dniErr = if (pd.dni.isBlank()) "El DNI es requerido" else null
        val firstNameErr = if (pd.firstName.isBlank()) "El nombre es requerido" else null
        val lastNameErr = if (pd.lastName.isBlank()) "El apellido es requerido" else null
        val addressErr = if (pd.address.isBlank()) "La dirección es requerida" else null
        val phoneErr = if (pd.phone.length < 9) "Mínimo 9 dígitos" else null
        val emailErr = when {
            pd.email.isBlank() -> "El correo es requerido"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(pd.email).matches() -> "Formato inválido"
            else -> null
        }
        _uiState.update {
            it.copy(
                personalData = it.personalData.copy(
                    dniError = dniErr,
                    firstNameError = firstNameErr,
                    lastNameError = lastNameErr,
                    addressError = addressErr,
                    phoneError = phoneErr,
                    emailError = emailErr
                )
            )
        }
        return listOf(dniErr, firstNameErr, lastNameErr, addressErr, phoneErr, emailErr).all { it == null }
    }

    // ── Education handlers ───────────────────────────────────────────────────

    fun onUniversityNameChange(value: String) =
        _uiState.update { it.copy(education = it.education.copy(universityName = value, universityNameError = null)) }

    fun onUniversityCareerChange(value: String) =
        _uiState.update { it.copy(education = it.education.copy(universityCareer = value)) }

    fun onUniversityStartDateChange(value: String) =
        _uiState.update { it.copy(education = it.education.copy(universityStartDate = value)) }

    fun onUniversityEndDateChange(value: String) =
        _uiState.update { it.copy(education = it.education.copy(universityEndDate = value)) }

    fun onStillAttendingChange(value: Boolean) =
        _uiState.update {
            it.copy(
                education = it.education.copy(
                    stillAttending = value,
                    universityEndDate = if (value) "" else it.education.universityEndDate
                )
            )
        }

    fun onSchoolNameChange(value: String) =
        _uiState.update { it.copy(education = it.education.copy(schoolName = value, schoolNameError = null)) }

    fun onMaxAcademicLevelChange(value: AcademicLevel) =
        _uiState.update { it.copy(education = it.education.copy(maxAcademicLevel = value)) }

    fun onNextFromEducation() {
        if (validateEducation()) setShowDialog(true)
    }

    private fun validateEducation(): Boolean {
        val ed = _uiState.value.education
        val uniErr = if (ed.universityName.isBlank()) "El nombre de la institución es requerido" else null
        val schoolErr = if (ed.schoolName.isBlank()) "El nombre del colegio es requerido" else null
        _uiState.update {
            it.copy(
                education = it.education.copy(
                    universityNameError = uniErr,
                    schoolNameError = schoolErr
                )
            )
        }
        return uniErr == null && schoolErr == null
    }

    // ── Certificates handlers ────────────────────────────────────────────────

    fun onCertNameChange(value: String) =
        _uiState.update { it.copy(certificates = it.certificates.copy(currentName = value, nameError = null)) }

    fun onCertInstitutionChange(value: String) =
        _uiState.update { it.copy(certificates = it.certificates.copy(currentInstitution = value, institutionError = null)) }

    fun onCertDateChange(value: String) =
        _uiState.update { it.copy(certificates = it.certificates.copy(currentObtainedDate = value)) }

    fun onCertDescriptionChange(value: String) =
        _uiState.update { it.copy(certificates = it.certificates.copy(currentDescription = value)) }

    fun addCertificate() {
        val certs = _uiState.value.certificates
        val nameErr = if (certs.currentName.isBlank()) "El nombre del certificado es requerido" else null
        val instErr = if (certs.currentInstitution.isBlank()) "La institución es requerida" else null
        if (nameErr != null || instErr != null) {
            _uiState.update { it.copy(certificates = it.certificates.copy(nameError = nameErr, institutionError = instErr)) }
            return
        }
        val newCert = Certificate(
            id = System.currentTimeMillis().toInt(),
            name = certs.currentName,
            institution = certs.currentInstitution,
            obtainedDate = certs.currentObtainedDate,
            description = certs.currentDescription
        )
        _uiState.update {
            it.copy(
                certificates = it.certificates.copy(
                    savedCertificates = it.certificates.savedCertificates + newCert,
                    currentName = "",
                    currentInstitution = "",
                    currentObtainedDate = "",
                    currentDescription = "",
                    nameError = null,
                    institutionError = null
                )
            )
        }
    }

    fun removeCertificate(certificate: Certificate) =
        _uiState.update {
            it.copy(
                certificates = it.certificates.copy(
                    savedCertificates = it.certificates.savedCertificates.filter { c -> c.id != certificate.id }
                )
            )
        }

    // ── Navigation ───────────────────────────────────────────────────────────

    fun setShowDialog(show: Boolean) =
        _uiState.update { it.copy(showConfirmDialog = show) }

    fun confirmAndContinue() {
        _uiState.update { state ->
            val nextStep = when (state.currentStep) {
                RegistrationStep.PERSONAL_DATA -> RegistrationStep.EDUCATION
                RegistrationStep.EDUCATION -> RegistrationStep.CERTIFICATES
                RegistrationStep.CERTIFICATES -> state.currentStep
            }
            state.copy(currentStep = nextStep, showConfirmDialog = false)
        }
    }

    fun onBackClicked() {
        _uiState.update { state ->
            val prevStep = when (state.currentStep) {
                RegistrationStep.PERSONAL_DATA -> state.currentStep
                RegistrationStep.EDUCATION -> RegistrationStep.PERSONAL_DATA
                RegistrationStep.CERTIFICATES -> RegistrationStep.EDUCATION
            }
            state.copy(currentStep = prevStep)
        }
    }

    // ── Persist all data to Room ─────────────────────────────────────────────

    fun saveCv() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, saveError = null) }
            runCatching {
                val state = _uiState.value
                repository.savePersonalData(state.personalData.toDomain())
                repository.saveEducation(state.education.toDomain())
                state.certificates.savedCertificates.forEach { repository.insertCertificate(it) }
            }.onSuccess {
                _uiState.update { it.copy(isSaving = false, saveSuccess = true) }
            }.onFailure { e ->
                _uiState.update { it.copy(isSaving = false, saveError = e.message ?: "Error desconocido") }
            }
        }
    }

    // ── Form state → Domain model mappers ───────────────────────────────────

    private fun PersonalDataFormState.toDomain() = PersonalData(
        dni = dni, firstName = firstName, lastName = lastName,
        address = address, maritalStatus = maritalStatus,
        photoUri = photoUri, phone = phone, email = email
    )

    private fun EducationFormState.toDomain() = Education(
        universityName = universityName, universityCareer = universityCareer,
        universityStartDate = universityStartDate, universityEndDate = universityEndDate,
        stillAttending = stillAttending, schoolName = schoolName,
        maxAcademicLevel = maxAcademicLevel
    )
}
