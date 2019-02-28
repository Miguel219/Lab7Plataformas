package com.example.lab7plataformas

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    internal lateinit var conexionSQLite: ConexionSQLite

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Se inicializa la base de datos
        conexionSQLite = ConexionSQLite(this)
        //Se traen los contactos de la base de datos
        MyApplication.contactArray = conexionSQLite.getContacts
        //Se utiliza adapter para agregar el arreglo al listview
        var adapter = ArrayAdapter(this, R.layout.listview_item, MyApplication.contactArray)
        adapter.notifyDataSetChanged()
        contactList.adapter = adapter
        //Al seleccionar un contacto se va a la activity de ver usuario
        contactList.setOnItemClickListener {
                parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            MyApplication.selectedPosition = position
            val intent: Intent = Intent(  this, Main3Activity::class.java)
            startActivity(intent)
            finish()
        }
        //Al dejar presionado un elemento de la lista se borra de base de datos
        contactList.onItemLongClickListener = object: AdapterView.OnItemLongClickListener{
            override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {
                conexionSQLite.deleteContact(MyApplication.contactArray[position])
                //Se actualizan los datos de los contactos
                MyApplication.contactArray = conexionSQLite.getContacts
                var adapter = ArrayAdapter(applicationContext, R.layout.contact_item, MyApplication.contactArray)
                contactList.adapter = adapter
                Toast.makeText(applicationContext, "Se ha eliminado el contacto", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        //Esta funcion va a la activity de crear un usuario
        button.setOnClickListener {

            val intent: Intent = Intent(  this, Main2Activity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
