package com.senati.cv.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "education")
data class EducationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val universityName: String,
    val universityCareer: String,
    val universityStartDate: String,
    val universityEndDate: String,
    val stillAttending: Boolean,
    val schoolName: String,
    val maxAcademicLevel: String
)
