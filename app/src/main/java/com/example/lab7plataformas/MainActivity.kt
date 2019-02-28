package com.example.lab7plataformas

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import com.example.lab7plataformas.adapter.ContactAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.button_main.*

class MainActivity : AppCompatActivity() {

    internal lateinit var conexionSQLite: ConexionSQLite

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.button_main)

        recycler.layoutManager=LinearLayoutManager(this,LinearLayout.VERTICAL,false)

        //Se inicializa la base de datos
        conexionSQLite = ConexionSQLite(this)
        //Se traen los contactos de la base de datos
        MyApplication.contactArray = conexionSQLite.getContacts
        //Se utiliza adapter para agregar el arreglo al listview
        var adapter = ContactAdapter(this, MyApplication.contactArray)
        recycler.adapter = adapter
        //Esta funcion va a la activity de crear un usuario
        add.setOnClickListener {

            val intent: Intent = Intent(  this, Main2Activity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
