package com.senati.cv.data.local.dao

import androidx.room.*
import com.senati.cv.data.local.entities.PersonalInfoEntity
import kotlinx.coroutines.flow.Flow

/**
 * Interface de acceso a datos (DAO) para la información personal.
 */
@Dao
interface PersonalInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersonalInfo(personalInfo: PersonalInfoEntity): Long

    @Query("SELECT * FROM personal_info ORDER BY id DESC LIMIT 1")
    fun getLatestPersonalInfo(): Flow<PersonalInfoEntity?>

    @Query("SELECT * FROM personal_info WHERE id = :id")
    suspend fun getPersonalInfoById(id: Long): PersonalInfoEntity?
}
