package com.senati.cv.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.senati.cv.domain.model.Certificate
import com.senati.cv.ui.components.CvTextField
import com.senati.cv.viewmodel.CertificatesFormState

@Composable
fun CertificatesStep(
    state: CertificatesFormState,
    onNameChange: (String) -> Unit,
    onInstitutionChange: (String) -> Unit,
    onDateChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onAddCertificate: () -> Unit,
    onRemoveCertificate: (Certificate) -> Unit,
    onSave: () -> Unit,
    onBackClicked: () -> Unit,
    isSaving: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Certificados",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Agregar Certificado",
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.labelLarge
                )
                CvTextField(
                    value = state.currentName,
                    onValueChange = onNameChange,
                    label = "Nombre del Certificado",
                    error = state.nameError
                )
                CvTextField(
                    value = state.currentInstitution,
                    onValueChange = onInstitutionChange,
                    label = "Institución Emisora",
                    error = state.institutionError
                )
                CvTextField(
                    value = state.currentObtainedDate,
                    onValueChange = onDateChange,
                    label = "Fecha de Obtención",
                    placeholder = "MM/AAAA"
                )
                CvTextField(
                    value = state.currentDescription,
                    onValueChange = onDescriptionChange,
                    label = "Descripción (opcional)"
                )
                Button(
                    onClick = onAddCertificate,
                    modifier = Modifier.align(Alignment.End),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Agregar")
                }
            }
        }

        if (state.savedCertificates.isNotEmpty()) {
            Text(
                text = "Certificados agregados (${state.savedCertificates.size})",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            state.savedCertificates.forEach { cert ->
                CertificateCard(certificate = cert, onRemove = { onRemoveCertificate(cert) })
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
                shape = MaterialTheme.shapes.medium,
                enabled = !isSaving
            ) { Text("Atrás") }

            Button(
                onClick = onSave,
                modifier = Modifier.weight(1f),
                shape = MaterialTheme.shapes.medium,
                enabled = !isSaving
            ) {
                if (isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Guardar CV")
                }
            }
        }
    }
}

@Composable
private fun CertificateCard(
    certificate: Certificate,
    onRemove: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(certificate.name, fontWeight = FontWeight.SemiBold)
                Text(certificate.institution, style = MaterialTheme.typography.bodySmall)
                if (certificate.obtainedDate.isNotBlank()) {
                    Text(certificate.obtainedDate, style = MaterialTheme.typography.labelSmall)
                }
            }
            IconButton(onClick = onRemove) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Eliminar certificado",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
