package com.senati.cv.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.senati.cv.ui.components.CvTextField
import com.senati.cv.viewmodel.CvUiState

/**
 * Pantalla de registro. Es una "Stateless Composable" ya que recibe el estado
 * y los eventos, facilitando las pruebas y la previsualización.
 */
@Composable
fun CvRegisterScreen(
    uiState: CvUiState,
    onNombreChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onTelefonoChange: (String) -> Unit,
    onSiguienteClick: () -> Unit,
    onConfirmar: () -> Unit,
    onCancelarDialogo: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Datos Personales",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        // Usamos nuestro componente personalizado
        CvTextField(
            value = uiState.nombre,
            onValueChange = onNombreChange,
            label = "Nombre Completo",
            error = uiState.nombreError
        )

        CvTextField(
            value = uiState.email,
            onValueChange = onEmailChange,
            label = "Correo Electrónico",
            error = uiState.emailError,
            keyboardType = KeyboardType.Email
        )

        CvTextField(
            value = uiState.telefono,
            onValueChange = onTelefonoChange,
            label = "Teléfono",
            error = uiState.telefonoError,
            keyboardType = KeyboardType.Phone
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onSiguienteClick,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Siguiente")
        }
    }

    // Diálogo de confirmación
    if (uiState.mostrarDialogo) {
        AlertDialog(
            onDismissRequest = onCancelarDialogo,
            title = { Text("¿Deseas continuar?") },
            text = { Text("Se validarán tus datos antes de pasar a la siguiente sección.") },
            confirmButton = {
                TextButton(onClick = {
                    onConfirmar()
                    Toast.makeText(context, "Datos validados correctamente", Toast.LENGTH_SHORT).show()
                }) {
                    Text("Sí, Siguiente")
                }
            },
            dismissButton = {
                TextButton(onClick = onCancelarDialogo) {
                    Text("Revisar")
                }
            }
        )
    }
}
