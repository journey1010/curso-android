package com.senati.cv.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.senati.cv.ui.theme.CvTheme

/**
 * Componente reutilizable para los campos de texto del formulario.
 * Aplicamos State Hoisting: el componente no "sabe" qué valor tiene, 
 * solo lo recibe y avisa cuando cambia, facilitando el testing y la modularidad.
 */
@Composable
fun CvTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    error: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        isError = error != null,
        supportingText = {
            error?.let { Text(it) }
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = modifier.fillMaxWidth(),
        singleLine = true
    )
}

/**
 * Área de Previsualización (Preview)
 */
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Modo Oscuro")
@Preview(showBackground = true, name = "Modo Claro")
@Composable
fun CvTextFieldPreview() {
    CvTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            CvTextField(
                value = "Juan Perez",
                onValueChange = {},
                label = "Nombre Completo"
            )
            CvTextField(
                value = "email-invalido",
                onValueChange = {},
                label = "Correo Electrónico",
                error = "El formato de correo no es válido"
            )
        }
    }
}
