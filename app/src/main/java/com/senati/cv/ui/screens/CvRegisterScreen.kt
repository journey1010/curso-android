package com.senati.cv.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.weight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.senati.cv.domain.model.AcademicLevel
import com.senati.cv.domain.model.Certificate
import com.senati.cv.domain.model.MaritalStatus
import com.senati.cv.domain.model.RegistrationStep
import com.senati.cv.ui.components.StepIndicator
import com.senati.cv.viewmodel.CvUiState

@Composable
fun CvRegisterScreen(
    uiState: CvUiState,
    // Personal data
    onDniChange: (String) -> Unit,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onMaritalStatusChange: (MaritalStatus) -> Unit,
    onPhotoUriChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onNextFromPersonalData: () -> Unit,
    // Education
    onUniversityNameChange: (String) -> Unit,
    onUniversityCareerChange: (String) -> Unit,
    onUniversityStartDateChange: (String) -> Unit,
    onUniversityEndDateChange: (String) -> Unit,
    onStillAttendingChange: (Boolean) -> Unit,
    onSchoolNameChange: (String) -> Unit,
    onMaxAcademicLevelChange: (AcademicLevel) -> Unit,
    onNextFromEducation: () -> Unit,
    // Certificates
    onCertNameChange: (String) -> Unit,
    onCertInstitutionChange: (String) -> Unit,
    onCertDateChange: (String) -> Unit,
    onCertDescriptionChange: (String) -> Unit,
    onAddCertificate: () -> Unit,
    onRemoveCertificate: (Certificate) -> Unit,
    onSaveCv: () -> Unit,
    // Navigation
    onBackClicked: () -> Unit,
    onConfirm: () -> Unit,
    onDismissDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        StepIndicator(
            currentStep = uiState.currentStep,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Box(modifier = Modifier.weight(1f)) {
            when (uiState.currentStep) {
                RegistrationStep.PERSONAL_DATA -> PersonalDataStep(
                    state = uiState.personalData,
                    onDniChange = onDniChange,
                    onFirstNameChange = onFirstNameChange,
                    onLastNameChange = onLastNameChange,
                    onAddressChange = onAddressChange,
                    onMaritalStatusChange = onMaritalStatusChange,
                    onPhotoUriChange = onPhotoUriChange,
                    onPhoneChange = onPhoneChange,
                    onEmailChange = onEmailChange,
                    onNextClicked = onNextFromPersonalData
                )

                RegistrationStep.EDUCATION -> EducationStep(
                    state = uiState.education,
                    onUniversityNameChange = onUniversityNameChange,
                    onUniversityCareerChange = onUniversityCareerChange,
                    onStartDateChange = onUniversityStartDateChange,
                    onEndDateChange = onUniversityEndDateChange,
                    onStillAttendingChange = onStillAttendingChange,
                    onSchoolNameChange = onSchoolNameChange,
                    onMaxAcademicLevelChange = onMaxAcademicLevelChange,
                    onNextClicked = onNextFromEducation,
                    onBackClicked = onBackClicked
                )

                RegistrationStep.CERTIFICATES -> CertificatesStep(
                    state = uiState.certificates,
                    onNameChange = onCertNameChange,
                    onInstitutionChange = onCertInstitutionChange,
                    onDateChange = onCertDateChange,
                    onDescriptionChange = onCertDescriptionChange,
                    onAddCertificate = onAddCertificate,
                    onRemoveCertificate = onRemoveCertificate,
                    onSave = onSaveCv,
                    onBackClicked = onBackClicked,
                    isSaving = uiState.isSaving
                )
            }

            if (uiState.showConfirmDialog) {
                AlertDialog(
                    onDismissRequest = onDismissDialog,
                    title = { Text("Confirmar datos") },
                    text = { Text("¿Los datos son correctos? Continuarás al siguiente paso.") },
                    confirmButton = {
                        TextButton(onClick = onConfirm) { Text("Continuar") }
                    },
                    dismissButton = {
                        TextButton(onClick = onDismissDialog) { Text("Revisar") }
                    }
                )
            }
        }
    }
}
