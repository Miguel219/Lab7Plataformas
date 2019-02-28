package com.example.lab7plataformas

import android.graphics.Bitmap

class Contact(name: String, phone: String, email: String, image: Bitmap) {

    var id: Int = 0
    var name: String = name
    var phone: String = phone
    var email: String = email
    var image: Bitmap = image
    override fun toString(): String {
        return "$name - $phone"
    }
}