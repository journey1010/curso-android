package com.senati.cv.domain.model

data class PersonalData(
    val dni: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val address: String = "",
    val maritalStatus: MaritalStatus = MaritalStatus.SINGLE,
    val photoUri: String = "",
    val phone: String = "",
    val email: String = ""
)
