package com.senati.cv.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.senati.cv.data.local.dao.CertificateDao
import com.senati.cv.data.local.dao.EducationDao
import com.senati.cv.data.local.dao.PersonalDataDao
import com.senati.cv.data.local.entity.CertificateEntity
import com.senati.cv.data.local.entity.EducationEntity
import com.senati.cv.data.local.entity.PersonalDataEntity

@Database(
    entities = [PersonalDataEntity::class, EducationEntity::class, CertificateEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CvDatabase : RoomDatabase() {

    abstract fun personalDataDao(): PersonalDataDao
    abstract fun educationDao(): EducationDao
    abstract fun certificateDao(): CertificateDao

    companion object {
        @Volatile
        private var instance: CvDatabase? = null

        fun getInstance(context: Context): CvDatabase =
            instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    CvDatabase::class.java,
                    "cv_database"
                ).build().also { instance = it }
            }
    }
}
