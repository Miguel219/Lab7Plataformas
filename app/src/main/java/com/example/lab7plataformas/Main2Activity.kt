package com.example.lab7plataformas

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main2.*
import android.provider.MediaStore
import android.graphics.Bitmap
import android.R.attr.data
import android.graphics.drawable.BitmapDrawable







class Main2Activity : AppCompatActivity() {

    var hasImage = false
    internal lateinit var conexionSQLite: ConexionSQLite

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        //Se inicializa la base de datos
        conexionSQLite = ConexionSQLite(this)
        //regresa al menu principal
        button2.setOnClickListener {
            val intent: Intent = Intent(  this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        //Se crea el contacto con la informacion ingresada por el usuario
        button3.setOnClickListener {
            if((currentName.text.toString().length!=0)and(currentPhone.text.toString().length==8)and(currentPhone.text.toString().length!=0)and(hasImage)) {
                //Se convierte la imagen a blob
                val bitmap = (imageView.getDrawable() as BitmapDrawable).bitmap
                //Se crea un contacto para guardarlo en base de datos
                var currentContact: Contact = Contact(currentName.text.toString(), currentPhone.text.toString(), currentEmail.text.toString(),bitmap)
                //Se guarda em base de datos
                conexionSQLite.addContact(currentContact)
                //Se despliega un Toast
                Toast.makeText(this, "Se ha creado el contacto", Toast.LENGTH_SHORT).show()
                val intent: Intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this, "Ingresa correctamente la información", Toast.LENGTH_SHORT).show()
            }
        }
        //Al precionar a la inmagen se puede cambiar por otra en la galeria
        imageView.setOnClickListener {
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
        if (photoUri!=null) {
            val selectedImage = MediaStore.Images.Media.getBitmap(this.contentResolver, photoUri)
            imageView.setImageBitmap(selectedImage)
            hasImage = true
        }
    }
}
