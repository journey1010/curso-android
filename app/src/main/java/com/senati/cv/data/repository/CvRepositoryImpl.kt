package com.senati.cv.data.repository

import com.senati.cv.data.local.dao.CertificateDao
import com.senati.cv.data.local.dao.EducationDao
import com.senati.cv.data.local.dao.PersonalDataDao
import com.senati.cv.data.local.entity.CertificateEntity
import com.senati.cv.data.local.entity.EducationEntity
import com.senati.cv.data.local.entity.PersonalDataEntity
import com.senati.cv.domain.model.AcademicLevel
import com.senati.cv.domain.model.Certificate
import com.senati.cv.domain.model.Education
import com.senati.cv.domain.model.MaritalStatus
import com.senati.cv.domain.model.PersonalData
import com.senati.cv.domain.repository.CvRepository

class CvRepositoryImpl(
    private val personalDataDao: PersonalDataDao,
    private val educationDao: EducationDao,
    private val certificateDao: CertificateDao
) : CvRepository {

    override suspend fun savePersonalData(data: PersonalData) {
        val existing = personalDataDao.get()
        if (existing != null) {
            personalDataDao.update(data.toEntity().copy(id = existing.id))
        } else {
            personalDataDao.insert(data.toEntity())
        }
    }

    override suspend fun getPersonalData(): PersonalData? =
        personalDataDao.get()?.toDomain()

    override suspend fun saveEducation(education: Education) {
        val existing = educationDao.get()
        if (existing != null) {
            educationDao.update(education.toEntity().copy(id = existing.id))
        } else {
            educationDao.insert(education.toEntity())
        }
    }

    override suspend fun getEducation(): Education? =
        educationDao.get()?.toDomain()

    override suspend fun insertCertificate(certificate: Certificate) {
        certificateDao.insert(certificate.toEntity())
    }

    override suspend fun deleteCertificate(certificate: Certificate) {
        certificateDao.delete(certificate.toEntity())
    }

    override suspend fun getAllCertificates(): List<Certificate> =
        certificateDao.getAll().map { it.toDomain() }

    // ── Mappers ─────────────────────────────────────────────────────────────

    private fun PersonalData.toEntity() = PersonalDataEntity(
        dni = dni,
        firstName = firstName,
        lastName = lastName,
        address = address,
        maritalStatus = maritalStatus.name,
        photoUri = photoUri,
        phone = phone,
        email = email
    )

    private fun PersonalDataEntity.toDomain() = PersonalData(
        dni = dni,
        firstName = firstName,
        lastName = lastName,
        address = address,
        maritalStatus = runCatching { MaritalStatus.valueOf(maritalStatus) }.getOrDefault(MaritalStatus.SINGLE),
        photoUri = photoUri,
        phone = phone,
        email = email
    )

    private fun Education.toEntity() = EducationEntity(
        universityName = universityName,
        universityCareer = universityCareer,
        universityStartDate = universityStartDate,
        universityEndDate = universityEndDate,
        stillAttending = stillAttending,
        schoolName = schoolName,
        maxAcademicLevel = maxAcademicLevel.name
    )

    private fun EducationEntity.toDomain() = Education(
        universityName = universityName,
        universityCareer = universityCareer,
        universityStartDate = universityStartDate,
        universityEndDate = universityEndDate,
        stillAttending = stillAttending,
        schoolName = schoolName,
        maxAcademicLevel = runCatching { AcademicLevel.valueOf(maxAcademicLevel) }.getOrDefault(AcademicLevel.SECONDARY)
    )

    private fun Certificate.toEntity() = CertificateEntity(
        id = id,
        name = name,
        institution = institution,
        obtainedDate = obtainedDate,
        description = description
    )

    private fun CertificateEntity.toDomain() = Certificate(
        id = id,
        name = name,
        institution = institution,
        obtainedDate = obtainedDate,
        description = description
    )
}
