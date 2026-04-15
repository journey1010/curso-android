package com.senati.cv

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.senati.cv.data.local.AppDatabase
import com.senati.cv.data.local.entities.AcademicInfoEntity
import com.senati.cv.data.local.entities.PersonalInfoEntity
import com.senati.cv.ui.theme.CvTheme
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

/**
 * Actividad para visualizar el CV guardado.
 */
class CvViewActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val db = AppDatabase.getDatabase(this)
        
        setContent {
            CvTheme {
                Scaffold { padding ->
                    // Obtenemos el último perfil guardado
                    val personalInfo by db.personalInfoDao().getLatestPersonalInfo().collectAsState(initial = null)
                    
                    personalInfo?.let { info ->
                        // Si hay perfil, obtenemos su info académica asociada
                        val academicList by db.academicInfoDao().getAcademicInfoByPersonalId(info.id).collectAsState(initial = emptyList())
                        
                        CvDetailsScreen(
                            modifier = Modifier.padding(padding),
                            personalInfo = info,
                            academicList = academicList
                        )
                    } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No hay datos guardados")
                    }
                }
            }
        }
    }
}

@Composable
fun CvDetailsScreen(
    modifier: Modifier,
    personalInfo: PersonalInfoEntity,
    academicList: List<AcademicInfoEntity>
) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("Currículum Vitae", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
            
            personalInfo.photo?.let {
                val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier.size(150.dp).padding(vertical = 16.dp)
                )
            }

            InfoRow("Nombre:", personalInfo.name)
            InfoRow("Email:", personalInfo.email)
            InfoRow("Teléfono:", personalInfo.phone)
            InfoRow("Dirección:", personalInfo.address)

            Divider(modifier = Modifier.padding(vertical = 16.dp))
            Text("Formación Académica", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        }

        items(academicList) { item ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(item.career, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(item.university)
                    Text("Año: ${item.year}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(label, fontWeight = FontWeight.Bold, modifier = Modifier.width(80.dp))
        Text(value)
    }
}
