package com.senati.cv.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.senati.cv.data.local.dao.AcademicInfoDao
import com.senati.cv.data.local.dao.PersonalInfoDao
import com.senati.cv.data.local.entities.AcademicInfoEntity
import com.senati.cv.data.local.entities.PersonalInfoEntity

/**
 * Base de datos Room de la aplicación.
 * Define las entidades y los DAOs asociados.
 */
@Database(
    entities = [PersonalInfoEntity::class, AcademicInfoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CvDatabase : RoomDatabase() {
    abstract fun personalInfoDao(): PersonalInfoDao
    abstract fun academicInfoDao(): AcademicInfoDao
}
