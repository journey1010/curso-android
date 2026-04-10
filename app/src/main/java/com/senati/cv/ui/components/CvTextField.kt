// Definición del paquete: organiza el componente en la capa de UI (User Interface)
package com.senati.cv.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

/**
 * Componente reutilizable para los campos de texto del formulario.
 * * Aplicamos State Hoisting: el componente no "sabe" qué valor tiene, 
 * solo lo recibe y avisa cuando cambia, facilitando el testing y la modularidad.
 */
@Composable
fun CvTextField(
    value: String,                    // El texto actual que se muestra (Estado)
    onValueChange: (String) -> Unit,  // Evento que se dispara al escribir (Lambdas)
    label: String,                    // Etiqueta flotante del campo
    modifier: Modifier = Modifier,    // Permite al padre modificar el diseño (padding, etc.)
    error: String? = null,            // Mensaje opcional: si existe, activa el estado de error
    keyboardType: KeyboardType = KeyboardType.Text // Define si es numérico, email, etc.
) {
    // Implementación de OutlinedTextField (estilo de borde definido por Material 3)
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },

        // isError cambia el color del borde y label a rojo automáticamente si no es null
        isError = error != null,

        // supportingText es el pequeño texto de ayuda o error debajo del campo
        supportingText = {
            error?.let { Text(it) } // Si error es distinto de null, renderiza el Text
        },

        // Configura el comportamiento del teclado (ej: mostrar @ si es email)
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),

        // El modificador asegura que el campo use todo el ancho disponible por defecto
        modifier = modifier.fillMaxWidth(),

        // Restringe el input a una sola línea para formularios más limpios
        singleLine = true
    )
}

/**
 * Área de Previsualización (Preview)
 * Permite ver el diseño en Android Studio sin ejecutar la app en un emulador.
 */
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Modo Oscuro") // Prueba el Dark Mode
@Preview(showBackground = true, name = "Modo Claro")                    // Prueba el Light Mode
@Composable
fun CvTextFieldPreview() {
    // Aplicamos el tema personalizado del proyecto
    CvTheme {
        // Usamos un Column para mostrar varios estados del componente a la vez
        Column(modifier = Modifier.padding(16.dp)) {

            // Caso 1: Uso normal del componente
            CvTextField(
                value = "Gino Espinoza",
                onValueChange = {}, // Lambda vacía para fines de preview
                label = "Nombre Completo"
            )

            // Caso 2: Visualización de estado de validación fallida
            CvTextField(
                value = "email-invalido",
                onValueChange = {},
                label = "Correo Electrónico",
                error = "El formato de correo no es válido"
            )
        }
    }
}
