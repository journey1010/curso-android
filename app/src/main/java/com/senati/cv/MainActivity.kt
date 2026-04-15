package com.senati.cv

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.senati.cv.data.local.AppDatabase
import com.senati.cv.presentation.form.CvFormViewModel
import com.senati.cv.presentation.screens.CvFormScreen
import com.senati.cv.ui.theme.CvTheme
import com.senati.cv.data.local.entities.PersonalInfoEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Actividad Principal: Maneja el formulario de 2 pasos.
 * Usa Hilt para inyectar el ViewModel y validadores.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: CvFormViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CvTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CvFormScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding), // <--- Se aplica el padding aquí
                        onSaveClick = { saveCvToDatabase() }
                    )
                }
            }
        }
    }

    /**
     * Guarda el CV en SQLite. Según el requerimiento, la lógica de BD 
     * no debe estar en el ViewModel. Se maneja aquí usando Coroutines.
     */
    private fun saveCvToDatabase() {
        val state = viewModel.uiState.value
        val academicList = viewModel.academicList.toList()

        if (academicList.isEmpty()) {
            Toast.makeText(this, "Añade al menos un registro académico", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                // Instanciamos la base de datos manualmente (SIN Hilt para la BD)
                val db = AppDatabase.getDatabase(this@MainActivity)
                
                // 1. Guardar Información Personal
                val personalInfo = PersonalInfoEntity(
                    name = state.name,
                    email = state.email,
                    phone = state.phone,
                    address = state.address,
                    photo = state.photo
                )
                val personalId = db.personalInfoDao().insertPersonalInfo(personalInfo)

                // 2. Guardar Información Académica vinculada (Relación 1:N)
                val academicEntities = academicList.map { 
                    it.copy(personalInfoId = personalId) 
                }
                db.academicInfoDao().insertAllAcademicInfo(academicEntities)

                Toast.makeText(this@MainActivity, "¡CV Guardado con éxito!", Toast.LENGTH_SHORT).show()
                
                // Navegar a la actividad de visualización
                startActivity(Intent(this@MainActivity, CvViewActivity::class.java))
                
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Error al guardar: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
