package com.senati.cv.data.local.dao

import androidx.room.*
import com.senati.cv.data.local.entities.AcademicInfoEntity
import kotlinx.coroutines.flow.Flow

/**
 * Interface de acceso a datos (DAO) para la información académica.
 */
@Dao
interface AcademicInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAcademicInfo(academicInfo: AcademicInfoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAcademicInfo(academicList: List<AcademicInfoEntity>)

    @Query("SELECT * FROM academic_info WHERE personalInfoId = :personalId")
    fun getAcademicInfoByPersonalId(personalId: Long): Flow<List<AcademicInfoEntity>>

    @Delete
    suspend fun deleteAcademicInfo(academicInfo: AcademicInfoEntity)
}
