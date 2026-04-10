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

    // El ViewModel se inicializa usando el delegado 'by viewModels()'
    // Esto asegura que el ViewModel sobreviva a cambios de configuración (como rotar la pantalla)
    private val viewModel: CvViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Habilita el diseño de borde a borde (edge-to-edge)
        enableEdgeToEdge()
        
        setContent {
            CvTheme {
                // Obtenemos el estado actual del ViewModel. 
                // Al usar 'collectAsState()', Compose se recompondrá automáticamente cada vez que el estado cambie.
                val uiState by viewModel.uiState.collectAsState()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Inyectamos la pantalla principal pasando el estado y los eventos (lambdas)
                    // Esta separación permite que la UI sea fácil de testear y previsualizar.
                    CvRegisterScreen(
                        modifier = Modifier.padding(innerPadding),
                        uiState = uiState,
                        onNameChange = viewModel::onNameChange,
                        onEmailChange = viewModel::onEmailChange,
                        onPhoneChange = viewModel::onPhoneChange,
                        onNextClicked = viewModel::onNextClicked,
                        onConfirm = viewModel::confirmAndContinue,
                        onDismissDialog = { viewModel.setShowDialog(false) }
                    )
                }
            }
        }
    }
}
