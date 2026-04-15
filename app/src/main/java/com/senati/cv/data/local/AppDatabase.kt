package com.senati.cv.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Objeto Singleton para gestionar la instancia de la base de datos de forma manual (SIN Hilt).
 */
object AppDatabase {
    private var INSTANCE: CvDatabase? = null

    // Migración de ejemplo: Versión 1 a 2 (aunque estemos en la 1)
    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            // Lógica de migración si se añadieran columnas en el futuro
            // db.execSQL("ALTER TABLE personal_info ADD COLUMN age INTEGER NOT NULL DEFAULT 0")
        }
    }

    /**
     * Obtiene la instancia de la base de datos.
     */
    fun getDatabase(context: Context): CvDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                CvDatabase::class.java,
                "cv_database"
            )
            .addMigrations(MIGRATION_1_2) // Soporte para migraciones
            .build()
            INSTANCE = instance
            instance
        }
    }
}
