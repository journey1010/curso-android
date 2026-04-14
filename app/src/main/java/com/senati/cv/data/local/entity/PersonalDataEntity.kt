package com.senati.cv.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "personal_data")
data class PersonalDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dni: String,
    val firstName: String,
    val lastName: String,
    val address: String,
    val maritalStatus: String,
    val photoUri: String,
    val phone: String,
    val email: String
)
