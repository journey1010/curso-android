package com.senati.cv.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.senati.cv.data.local.entity.CertificateEntity

@Dao
interface CertificateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: CertificateEntity): Long

    @Query("SELECT * FROM certificates")
    suspend fun getAll(): List<CertificateEntity>

    @Delete
    suspend fun delete(entity: CertificateEntity)

    @Update
    suspend fun update(entity: CertificateEntity)
}
