package com.example.lab7plataformas

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main4.*

class Main4Activity : AppCompatActivity() {

    internal lateinit var conexionSQLite: ConexionSQLite

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)
        //Se inicializa la base de datos
        conexionSQLite = ConexionSQLite(this)

        //Se inicializa la informacion del usuario
        var selectedContact: Contact = MyApplication.contactArray[MyApplication.selectedPosition]
        currentName2.setText(selectedContact.name)
        currentPhone2.setText(selectedContact.phone)
        currentEmail2.setText(selectedContact.email)
        imageView3.setImageBitmap(selectedContact.image)

        //Va a la activity de ver el contacto
        button6.setOnClickListener {
            val intent: Intent = Intent(  this, Main3Activity::class.java)
            startActivity(intent)
            finish()
        }

        button7.setOnClickListener {
            if((currentName2.text.toString().length!=0)and(currentPhone2.text.toString().length==8)and(currentPhone2.text.toString().length!=0)) {
                //Se convierte la imagen a blob
                val bitmap = (imageView3.getDrawable() as BitmapDrawable).bitmap
                //Se crea un contacto para guardarlo en base de datos
                var currentContact: Contact = Contact(currentName2.text.toString(), currentPhone2.text.toString(), currentEmail2.text.toString(),bitmap)
                currentContact.id = MyApplication.contactArray[MyApplication.selectedPosition].id
                //Se guarda em base de datos
                conexionSQLite.updateContact(currentContact)
                //Se actualizan los datos de los contactos
                MyApplication.contactArray = conexionSQLite.getContacts
                //Se despliega un Toast
                Toast.makeText(this, "Se ha editado el contacto", Toast.LENGTH_SHORT).show()
                val intent: Intent = Intent(this, Main3Activity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this, "Ingresa correctamente la información", Toast.LENGTH_SHORT).show()
            }
        }

        //Al precionar a la inmagen se puede cambiar por otra en la galeria
        imageView3.setOnClickListener {
            val PICK_PHOTO_CODE = 1046
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            if (intent.resolveActivity(getPackageManager()) != null) {
                // Bring up gallery to select a photo
                startActivityForResult(intent, PICK_PHOTO_CODE)
            }

        }
    }

    //Se setea la imagen al imageView al ser seleccionada
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val photoUri = data?.data
        // Do something with the photo based on Uri
        val selectedImage = MediaStore.Images.Media.getBitmap(this.contentResolver, photoUri)
        imageView3.setImageBitmap(selectedImage)
    }
}
