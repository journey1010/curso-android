package com.senati.cv.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.senati.cv.ui.components.CvTextField
import com.senati.cv.ui.theme.CvTheme
import com.senati.cv.viewmodel.CvUiState
import com.senati.cv.viewmodel.RegistrationStep

/**
 * Screen Principal: Actúa como el "Router" que decide qué vista cargar
 * basándose en el estado (currentStep).
 */
@Composable
fun CvRegisterScreen(
    uiState: CvUiState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onNextClicked: () -> Unit,
    onConfirm: () -> Unit,
    onDismissDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Similar a un switch en un controller de Laravel que carga diferentes vistas.
    Box(modifier = modifier.fillMaxSize()) {
        when (uiState.currentStep) {
            RegistrationStep.PERSONAL_DATA -> {
                PersonalDataStep(
                    uiState = uiState,
                    onNameChange = onNameChange,
                    onEmailChange = onEmailChange,
                    onPhoneChange = onPhoneChange,
                    onNextClicked = onNextClicked
                )
            }
            RegistrationStep.EDUCATION -> {
                // Siguiente pantalla (Placeholder por ahora)
                StepPlaceholder(title = "Formación Académica")
            }
            else -> {
                StepPlaceholder(title = "Próximamente...")
            }
        }

        // Diálogo de confirmación: Funciona como un "SweetAlert" o modal de confirmación
        if (uiState.showConfirmDialog) {
            AlertDialog(
                onDismissRequest = onDismissDialog,
                title = { Text("Confirmar Datos") },
                text = { Text("¿Deseas guardar estos datos y pasar al siguiente paso?") },
                confirmButton = {
                    TextButton(onClick = {
                        onConfirm()
                        Toast.makeText(context, "Paso completado", Toast.LENGTH_SHORT).show()
                    }) {
                        Text("Confirmar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = onDismissDialog) {
                        Text("Revisar")
                    }
                }
            )
        }
    }
}

/**
 * Vista específica para el Paso 1: Datos Personales.
 */
@Composable
fun PersonalDataStep(
    uiState: CvUiState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onNextClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Paso 1: Datos Personales",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        CvTextField(
            value = uiState.name,
            onValueChange = onNameChange,
            label = "Nombre Completo",
            error = uiState.nameError
        )

        CvTextField(
            value = uiState.email,
            onValueChange = onEmailChange,
            label = "Email",
            error = uiState.emailError,
            keyboardType = KeyboardType.Email
        )

        CvTextField(
            value = uiState.phone,
            onValueChange = onPhoneChange,
            label = "Teléfono",
            error = uiState.phoneError,
            keyboardType = KeyboardType.Phone
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onNextClicked,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Siguiente")
        }
    }
}

@Composable
fun StepPlaceholder(title: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = title, fontSize = 20.sp)
    }
}

@Preview(showSystemUi = true, name = "Paso 1: Datos Personales")
@Composable
fun CvRegisterScreenPreview() {
    CvTheme {
        CvRegisterScreen(
            uiState = CvUiState(
                name = "Juan Perez",
                email = "juan@example.com",
                phone = "987654321"
            ),
            onNameChange = {},
            onEmailChange = {},
            onPhoneChange = {},
            onNextClicked = {},
            onConfirm = {},
            onDismissDialog = {}
        )
    }
}

@Preview(showSystemUi = true, name = "Paso 1: Con Errores")
@Composable
fun CvRegisterScreenErrorPreview() {
    CvTheme {
        CvRegisterScreen(
            uiState = CvUiState(
                name = "",
                email = "correo-invalido",
                phone = "123",
                nameError = "El nombre es requerido",
                emailError = "Formato inválido",
                phoneError = "Mínimo 9 dígitos"
            ),
            onNameChange = {},
            onEmailChange = {},
            onPhoneChange = {},
            onNextClicked = {},
            onConfirm = {},
            onDismissDialog = {}
        )
    }
}

@Preview(showSystemUi = true, name = "Diálogo de Confirmación")
@Composable
fun CvRegisterScreenDialogPreview() {
    CvTheme {
        CvRegisterScreen(
            uiState = CvUiState(
                name = "Juan Perez",
                email = "juan@example.com",
                phone = "987654321",
                showConfirmDialog = true
            ),
            onNameChange = {},
            onEmailChange = {},
            onPhoneChange = {},
            onNextClicked = {},
            onConfirm = {},
            onDismissDialog = {}
        )
    }
}
