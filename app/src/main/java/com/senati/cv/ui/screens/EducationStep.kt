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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.senati.cv.domain.model.AcademicLevel
import com.senati.cv.ui.components.CvTextField
import com.senati.cv.viewmodel.EducationFormState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EducationStep(
    state: EducationFormState,
    onUniversityNameChange: (String) -> Unit,
    onUniversityCareerChange: (String) -> Unit,
    onStartDateChange: (String) -> Unit,
    onEndDateChange: (String) -> Unit,
    onStillAttendingChange: (Boolean) -> Unit,
    onSchoolNameChange: (String) -> Unit,
    onMaxAcademicLevelChange: (AcademicLevel) -> Unit,
    onNextClicked: () -> Unit,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    var academicLevelExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Formación Académica",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Universidad / Instituto",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        CvTextField(
            value = state.universityName,
            onValueChange = onUniversityNameChange,
            label = "Nombre de la Institución",
            error = state.universityNameError
        )

        CvTextField(
            value = state.universityCareer,
            onValueChange = onUniversityCareerChange,
            label = "Carrera / Especialidad"
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            CvTextField(
                value = state.universityStartDate,
                onValueChange = onStartDateChange,
                label = "Fecha Inicio",
                placeholder = "MM/AAAA",
                modifier = Modifier.weight(1f)
            )
            CvTextField(
                value = state.universityEndDate,
                onValueChange = onEndDateChange,
                label = "Fecha Salida",
                placeholder = "MM/AAAA",
                enabled = !state.stillAttending,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = state.stillAttending,
                onCheckedChange = onStillAttendingChange
            )
            Text(
                text = "Actualmente estudio aquí",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

        Text(
            text = "Educación Escolar",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        CvTextField(
            value = state.schoolName,
            onValueChange = onSchoolNameChange,
            label = "Nombre del Colegio",
            error = state.schoolNameError
        )

        ExposedDropdownMenuBox(
            expanded = academicLevelExpanded,
            onExpandedChange = { academicLevelExpanded = it }
        ) {
            OutlinedTextField(
                value = state.maxAcademicLevel.label,
                onValueChange = {},
                readOnly = true,
                label = { Text("Máximo Grado Académico") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = academicLevelExpanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
            )
            ExposedDropdownMenu(
                expanded = academicLevelExpanded,
                onDismissRequest = { academicLevelExpanded = false }
            ) {
                AcademicLevel.entries.forEach { level ->
                    DropdownMenuItem(
                        text = { Text(level.label) },
                        onClick = {
                            onMaxAcademicLevelChange(level)
                            academicLevelExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedButton(
                onClick = onBackClicked,
                modifier = Modifier.weight(1f),
                shape = MaterialTheme.shapes.medium
            ) { Text("Atrás") }

            Button(
                onClick = onNextClicked,
                modifier = Modifier.weight(1f),
                shape = MaterialTheme.shapes.medium
            ) { Text("Siguiente") }
        }
    }
}
