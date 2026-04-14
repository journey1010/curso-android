package com.senati.cv.domain.repository

import com.senati.cv.domain.model.Certificate
import com.senati.cv.domain.model.Education
import com.senati.cv.domain.model.PersonalData

interface CvRepository {
    suspend fun savePersonalData(data: PersonalData)
    suspend fun getPersonalData(): PersonalData?
    suspend fun saveEducation(education: Education)
    suspend fun getEducation(): Education?
    suspend fun insertCertificate(certificate: Certificate)
    suspend fun deleteCertificate(certificate: Certificate)
    suspend fun getAllCertificates(): List<Certificate>
}
