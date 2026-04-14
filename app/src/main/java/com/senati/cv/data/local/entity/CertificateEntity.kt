package com.senati.cv.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "certificates")
data class CertificateEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val institution: String,
    val obtainedDate: String,
    val description: String
)
