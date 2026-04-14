package com.senati.cv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.senati.cv.ui.screens.CvRegisterScreen
import com.senati.cv.ui.theme.CvTheme
import com.senati.cv.viewmodel.CvViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: CvViewModel by viewModels {
        (application as CvApplication).appContainer.cvViewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CvTheme {
                val uiState by viewModel.uiState.collectAsState()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CvRegisterScreen(
                        modifier = Modifier.padding(innerPadding),
                        uiState = uiState,
                        onDniChange = viewModel::onDniChange,
                        onFirstNameChange = viewModel::onFirstNameChange,
                        onLastNameChange = viewModel::onLastNameChange,
                        onAddressChange = viewModel::onAddressChange,
                        onMaritalStatusChange = viewModel::onMaritalStatusChange,
                        onPhotoUriChange = viewModel::onPhotoUriChange,
                        onPhoneChange = viewModel::onPhoneChange,
                        onEmailChange = viewModel::onEmailChange,
                        onNextFromPersonalData = viewModel::onNextFromPersonalData,
                        onUniversityNameChange = viewModel::onUniversityNameChange,
                        onUniversityCareerChange = viewModel::onUniversityCareerChange,
                        onUniversityStartDateChange = viewModel::onUniversityStartDateChange,
                        onUniversityEndDateChange = viewModel::onUniversityEndDateChange,
                        onStillAttendingChange = viewModel::onStillAttendingChange,
                        onSchoolNameChange = viewModel::onSchoolNameChange,
                        onMaxAcademicLevelChange = viewModel::onMaxAcademicLevelChange,
                        onNextFromEducation = viewModel::onNextFromEducation,
                        onCertNameChange = viewModel::onCertNameChange,
                        onCertInstitutionChange = viewModel::onCertInstitutionChange,
                        onCertDateChange = viewModel::onCertDateChange,
                        onCertDescriptionChange = viewModel::onCertDescriptionChange,
                        onAddCertificate = viewModel::addCertificate,
                        onRemoveCertificate = viewModel::removeCertificate,
                        onSaveCv = viewModel::saveCv,
                        onBackClicked = viewModel::onBackClicked,
                        onConfirm = viewModel::confirmAndContinue,
                        onDismissDialog = { viewModel.setShowDialog(false) }
                    )
                }
            }
        }
    }
}
