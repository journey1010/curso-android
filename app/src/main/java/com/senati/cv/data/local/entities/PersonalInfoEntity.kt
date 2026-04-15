package com.senati.cv.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad que representa la información personal del usuario en la base de datos Room.
 */
@Entity(tableName = "personal_info")
data class PersonalInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    // La foto se almacena como un arreglo de bytes (Blob en SQLite)
    val photo: ByteArray?
) {
    // Implementación manual de equals/hashCode por el ByteArray
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as PersonalInfoEntity
        if (id != other.id) return false
        if (name != other.name) return false
        if (email != other.email) return false
        if (phone != other.phone) return false
        if (address != other.address) return false
        if (photo != null) {
            if (other.photo == null) return false
            if (!photo.contentEquals(other.photo)) return false
        } else if (other.photo != null) return false
        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + phone.hashCode()
        result = 31 * result + address.hashCode()
        result = 31 * result + (photo?.contentHashCode() ?: 0)
        return result
    }
}
