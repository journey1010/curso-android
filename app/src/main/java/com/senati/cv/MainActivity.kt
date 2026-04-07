package com.senati.cv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.senati.cv.ui.screens.CvRegisterScreen
import com.senati.cv.ui.theme.CvTheme
import com.senati.cv.viewmodel.CvViewModel
import com.senati.cv.ui.components.CvTextField

class MainActivity : ComponentActivity() {
    
    // El ViewModel se mantiene en el Activity (o NavGraph) para sobrevivir a cambios de configuración
    private val viewModel: CvViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CvTheme {
                val uiState by viewModel.uiState.collectAsState()
                
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // La UI se delega a una Screen especializada
                    CvRegisterScreen(
                        modifier = Modifier.padding(innerPadding),
                        uiState = uiState,
                        onNombreChange = viewModel::onNombreChange,
                        onEmailChange = viewModel::onEmailChange,
                        onTelefonoChange = viewModel::onTelefonoChange,
                        onSiguienteClick = {
                            if (viewModel.validarDatos()) {
                                viewModel.mostrarConfirmacion(true)
                            }
                        },
                        onConfirmar = {
                            viewModel.mostrarConfirmacion(false)
                            // Aquí se navegaría a la siguiente pantalla (e.g., Formación Académica)
                        },
                        onCancelarDialogo = { viewModel.mostrarConfirmacion(false) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CvTextFieldPreview() {
    CvTheme {
        CvTextField(value = "Juan Perez", onValueChange = {}, label = "Nombre")
    }
}