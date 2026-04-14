package com.senati.cv.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.senati.cv.data.local.entity.PersonalDataEntity

@Dao
interface PersonalDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: PersonalDataEntity): Long

    @Query("SELECT * FROM personal_data ORDER BY id DESC LIMIT 1")
    suspend fun get(): PersonalDataEntity?

    @Update
    suspend fun update(entity: PersonalDataEntity)
}
