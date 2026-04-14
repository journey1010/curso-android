package com.senati.cv.domain.model

enum class MaritalStatus(val label: String) {
    SINGLE("Soltero/a"),
    MARRIED("Casado/a"),
    DIVORCED("Divorciado/a"),
    WIDOWED("Viudo/a"),
    COHABITING("Conviviente")
}

enum class AcademicLevel(val label: String) {
    PRIMARY("Primaria"),
    SECONDARY("Secundaria"),
    TECHNICAL("Técnico Superior"),
    BACHELOR("Bachiller"),
    LICENCIATURA("Licenciatura"),
    MASTERS("Maestría"),
    DOCTORATE("Doctorado")
}
