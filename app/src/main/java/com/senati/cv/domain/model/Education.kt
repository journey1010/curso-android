package com.senati.cv.domain.model

data class Education(
    val universityName: String = "",
    val universityCareer: String = "",
    val universityStartDate: String = "",
    val universityEndDate: String = "",
    val stillAttending: Boolean = false,
    val schoolName: String = "",
    val maxAcademicLevel: AcademicLevel = AcademicLevel.SECONDARY
)
