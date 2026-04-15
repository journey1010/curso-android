package com.senati.cv.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entidad que representa la información académica.
 * Tiene una relación de muchos a uno (N:1) con PersonalInfoEntity.
 */
@Entity(
    tableName = "academic_info",
    foreignKeys = [
        ForeignKey(
            entity = PersonalInfoEntity::class,
            parentColumns = ["id"],
            childColumns = ["personalInfoId"],
            onDelete = ForeignKey.CASCADE // Si se borra el perfil, se borra su info académica
        )
    ],
    indices = [Index(value = ["personalInfoId"])]
)
data class AcademicInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val personalInfoId: Long, // Clave foránea que apunta a PersonalInfoEntity
    val university: String,
    val career: String,
    val year: String
)
