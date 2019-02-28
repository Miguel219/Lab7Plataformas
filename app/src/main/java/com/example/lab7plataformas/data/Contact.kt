package com.example.lab7plataformas.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "contact_table")

data class Contact (
    var name: String,
    var phone: String,
    var email: String,
    var image: ByteArray
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}