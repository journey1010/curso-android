package com.senati.cv.presentation.screens

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.senati.cv.presentation.form.CvFormState
import com.senati.cv.presentation.form.CvFormViewModel
import com.senati.cv.data.local.entities.AcademicInfoEntity
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Pantalla principal del formulario (Step 1 y Step 2).
 */
@Composable
fun CvFormScreen(
    viewModel: CvFormViewModel,
    modifier: Modifier = Modifier,
    onSaveClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = if (state.currentStep == 1) "Paso 1: Datos Personales" else "Paso 2: Información Académica",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        if (state.currentStep == 1) {
            PersonalInfoStep(state, viewModel)
        } else {
            AcademicInfoStep(state, viewModel, onSaveClick)
        }
    }
}

@Composable
fun PersonalInfoStep(state: CvFormState, viewModel: CvFormViewModel) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var tempUri by remember { mutableStateOf<Uri?>(null) }

    // Función auxiliar para crear un archivo temporal para la cámara
    fun createImageFile(): Uri {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }

    // Launcher para Galería
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            context.contentResolver.openInputStream(it)?.use { stream ->
                viewModel.onPhotoChange(stream.readBytes())
            }
        }
    }

    // Launcher para Cámara
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            tempUri?.let { uri ->
                context.contentResolver.openInputStream(uri)?.use { stream ->
                    viewModel.onPhotoChange(stream.readBytes())
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Seleccionar Foto") },
            text = { Text("Elija una opción para cargar su foto de perfil.") },
            confirmButton = {
                TextButton(onClick = {
                    val uri = createImageFile()
                    tempUri = uri
                    cameraLauncher.launch(uri)
                    showDialog = false
                }) {
                    Text("Cámara")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    galleryLauncher.launch("image/*")
                    showDialog = false
                }) {
                    Text("Galería")
                }
            }
        )
    }

    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        item {
            OutlinedTextField(
                value = state.name,
                onValueChange = viewModel::onNameChange,
                label = { Text("Nombre Completo") },
                isError = state.nameError != null,
                supportingText = { state.nameError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = state.email,
                onValueChange = viewModel::onEmailChange,
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = state.emailError != null,
                supportingText = { state.emailError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = state.phone,
                onValueChange = viewModel::onPhoneChange,
                label = { Text("Teléfono") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                isError = state.phoneError != null,
                supportingText = { state.phoneError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = state.address,
                onValueChange = viewModel::onAddressChange,
                label = { Text("Dirección") },
                isError = state.addressError != null,
                supportingText = { state.addressError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))
            
            Button(onClick = { showDialog = true }) {
                Text("Cargar Foto (Cámara/Galería)")
            }

            state.photo?.let {
                val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                if (bitmap != null) {
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Foto seleccionada",
                        modifier = Modifier.size(120.dp).padding(top = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = viewModel::nextStep,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Siguiente")
            }
        }
    }
}

@Composable
fun AcademicInfoStep(
    state: CvFormState, 
    viewModel: CvFormViewModel,
    onSaveClick: () -> Unit
) {
    Column {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Añadir Educación", fontWeight = FontWeight.Bold)
                OutlinedTextField(
                    value = state.tempUniversity,
                    onValueChange = viewModel::onTempUniversityChange,
                    label = { Text("Universidad") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = state.tempCareer,
                    onValueChange = viewModel::onTempCareerChange,
                    label = { Text("Carrera") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = state.tempYear,
                    onValueChange = viewModel::onTempYearChange,
                    label = { Text("Año de Egreso") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                IconButton(
                    onClick = viewModel::addAcademicInfo,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Añadir")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Lista de Estudios:", fontWeight = FontWeight.Bold)

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(viewModel.academicList) { item ->
                ListItem(
                    headlineContent = { Text(item.career) },
                    supportingContent = { Text("${item.university} - ${item.year}") },
                    trailingContent = {
                        IconButton(onClick = { viewModel.removeAcademicInfo(item) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Borrar")
                        }
                    }
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = viewModel::previousStep) {
                Text("Volver")
            }
            Button(onClick = onSaveClick) {
                Text("Guardar CV")
            }
        }
    }
}
