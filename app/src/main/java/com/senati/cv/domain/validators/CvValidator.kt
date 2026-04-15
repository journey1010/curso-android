package com.senati.cv.domain.validators

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Validador para los campos del CV.
 * Inyectado con Hilt para demostrar el uso de DI en validadores.
 */
@Singleton
class CvValidator @Inject constructor() {

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPhone(phone: String): Boolean {
        // Validación simple de teléfono (ej. 9 dígitos)
        return phone.length >= 9 && phone.all { it.isDigit() }
    }

    fun isNotEmpty(text: String): Boolean {
        return text.trim().isNotEmpty()
    }

    fun isValidYear(year: String): Boolean {
        val currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
        val yearInt = year.toIntOrNull() ?: return false
        return yearInt in 1950..currentYear
    }
}
