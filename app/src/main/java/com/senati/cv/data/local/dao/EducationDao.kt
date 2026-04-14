package com.senati.cv.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.senati.cv.data.local.entity.EducationEntity

@Dao
interface EducationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: EducationEntity): Long

    @Query("SELECT * FROM education ORDER BY id DESC LIMIT 1")
    suspend fun get(): EducationEntity?

    @Update
    suspend fun update(entity: EducationEntity)
}
