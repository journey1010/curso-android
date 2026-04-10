// Paquete que define la pantalla principal dentro de la estructura de navegación
package com.senati.cv.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.senati.cv.ui.components.CvTextField
import com.senati.cv.ui.theme.CvTheme
import com.senati.cv.viewmodel.CvUiState

/**
 * Pantalla de registro de CV.
 * Es una "Stateless Composable": No gestiona su propio estado, solo lo "dibuja".
 * Recibe el estado (uiState) y los eventos (lambdas) desde un nivel superior (generalmente el NavHost).
 */
@Composable
fun CvRegisterScreen(
    uiState: CvUiState,               // Recibe el objeto inmutable con los datos actuales
    onNombreChange: (String) -> Unit, // Callback para cuando cambia el nombre
    onEmailChange: (String) -> Unit,  // Callback para cuando cambia el email
    onTelefonoChange: (String) -> Unit, // Callback para el teléfono
    onSiguienteClick: () -> Unit,     // Acción al presionar el botón principal
    onConfirmar: () -> Unit,          // Acción al aceptar el diálogo
    onCancelarDialogo: () -> Unit,    // Acción al cerrar el diálogo
    modifier: Modifier = Modifier
) {
    // Obtenemos el contexto actual de la aplicación para mostrar Toasts
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize() // Ocupa todo el espacio de la pantalla
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally, // Centra los elementos horizontalmente
        verticalArrangement = Arrangement.spacedBy(16.dp)    // Espaciado uniforme entre componentes
    ) {
        // Título de la sección usando la tipografía de Material 3
        Text(
            text = "Datos Personales",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        // Inserción de nuestros componentes personalizados CvTextField
        // Se conectan directamente con el estado y las funciones del ViewModel
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
            keyboardType = KeyboardType.Email // Configura el teclado para mostrar el @
        )

        CvTextField(
            value = uiState.telefono,
            onValueChange = onTelefonoChange,
            label = "Teléfono",
            error = uiState.telefonoError,
            keyboardType = KeyboardType.Phone // Configura el teclado numérico
        )

        // El Spacer con weight(1f) empuja el botón hacia la parte inferior
        Spacer(modifier = Modifier.weight(1f))

        // Botón principal de acción
        Button(
            onClick = onSiguienteClick,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Siguiente")
        }
    }

    /**
     * Lógica condicional para el Diálogo de Confirmación.
     * En Compose, los diálogos no se "llaman", se "muestran" según el estado.
     */
    if (uiState.mostrarDialogo) {
        AlertDialog(
            onDismissRequest = onCancelarDialogo,
            title = { Text("¿Deseas continuar?") },
            text = { Text("Se validarán tus datos antes de pasar a la siguiente sección.") },
            confirmButton = {
                TextButton(onClick = {
                    onConfirmar()
                    // Feedback visual rápido al usuario
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

/**
 * Previsualización estándar de la pantalla con datos de ejemplo.
 */
@Preview(showSystemUi = true)
@Composable
fun CvRegisterScreenPreview() {
    CvTheme {
        CvRegisterScreen(
            uiState = CvUiState(
                nombre = "Juan Perez",
                email = "juan@example.com",
                telefono = "987654321"
            ),
            onNombreChange = {},
            onEmailChange = {},
            onTelefonoChange = {},
            onSiguienteClick = {},
            onConfirmar = {},
            onCancelarDialogo = {}
        )
    }
}

/**
 * Previsualización específica para verificar cómo se ven los errores en la pantalla completa.
 */
@Preview(showSystemUi = true, name = "Con Errores")
@Composable
fun CvRegisterScreenErrorPreview() {
    CvTheme {
        CvRegisterScreen(
            uiState = CvUiState(
                nombre = "",
                email = "correo-invalido",
                telefono = "123",
                nombreError = "El nombre es obligatorio",
                emailError = "Formato de correo inválido",
                telefonoError = "Mínimo 9 dígitos"
            ),
            onNombreChange = {},
            onEmailChange = {},
            onTelefonoChange = {},
            onSiguienteClick = {},
            onConfirmar = {},
            onCancelarDialogo = {}
        )
    }
}