package com.senati.cv.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.senati.cv.domain.model.MaritalStatus
import com.senati.cv.ui.components.CvTextField
import com.senati.cv.ui.components.PhotoPicker
import com.senati.cv.viewmodel.PersonalDataFormState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalDataStep(
    state: PersonalDataFormState,
    onDniChange: (String) -> Unit,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onMaritalStatusChange: (MaritalStatus) -> Unit,
    onPhotoUriChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onNextClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    var maritalStatusExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Datos Personales",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        PhotoPicker(
            photoUri = state.photoUri,
            onPhotoSelected = onPhotoUriChange
        )

        CvTextField(
            value = state.dni,
            onValueChange = onDniChange,
            label = "DNI",
            error = state.dniError,
            keyboardType = KeyboardType.Number
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            CvTextField(
                value = state.firstName,
                onValueChange = onFirstNameChange,
                label = "Nombres",
                error = state.firstNameError,
                modifier = Modifier.weight(1f)
            )
            CvTextField(
                value = state.lastName,
                onValueChange = onLastNameChange,
                label = "Apellidos",
                error = state.lastNameError,
                modifier = Modifier.weight(1f)
            )
        }

        CvTextField(
            value = state.address,
            onValueChange = onAddressChange,
            label = "Dirección",
            error = state.addressError
        )

        ExposedDropdownMenuBox(
            expanded = maritalStatusExpanded,
            onExpandedChange = { maritalStatusExpanded = it }
        ) {
            OutlinedTextField(
                value = state.maritalStatus.label,
                onValueChange = {},
                readOnly = true,
                label = { Text("Estado Civil") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = maritalStatusExpanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
            )
            ExposedDropdownMenu(
                expanded = maritalStatusExpanded,
                onDismissRequest = { maritalStatusExpanded = false }
            ) {
                MaritalStatus.entries.forEach { status ->
                    DropdownMenuItem(
                        text = { Text(status.label) },
                        onClick = {
                            onMaritalStatusChange(status)
                            maritalStatusExpanded = false
                        }
                    )
                }
            }
        }

        CvTextField(
            value = state.phone,
            onValueChange = onPhoneChange,
            label = "Teléfono",
            error = state.phoneError,
            keyboardType = KeyboardType.Phone
        )

        CvTextField(
            value = state.email,
            onValueChange = onEmailChange,
            label = "Correo Electrónico",
            error = state.emailError,
            keyboardType = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onNextClicked,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Siguiente")
        }
    }
}
