package com.example.lab7plataformas

import android.app.Application

class MyApplication: Application() {
    //Se utiliza companion para que sea un objeto global
    companion object {
        //Arreglo de objetos ti[p contacto con la informacion de los contactos ingresados por el ususario
        var contactArray: ArrayList<Contact> = ArrayList()
        //La posicion que el usuario selecciono en la ListView
        var selectedPosition: Int = 0
    }
}