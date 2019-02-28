package com.example.lab7plataformas

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.BitmapFactory
import android.R.attr.bitmap
import android.content.ContentValues
import android.graphics.Bitmap
import java.io.ByteArrayOutputStream


class ConexionSQLite(context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VER) {

    companion object {
        private val DATABASE_VER = 1
        private val DATABASE_NAME = "LAB5DB.db"

        //Tabla
        private val TABLE_NAME = "Contact"
        private val COL_ID = "Id"
        private val COL_NAME = "Name"
        private val COL_EMAIL = "Email"
        private val COL_PHONE = "Phone"
        private val COL_PICTURE = "Picture"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY = ("CREATE TABLE $TABLE_NAME ( $COL_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, $COL_NAME TEXT, $COL_PHONE TEXT, $COL_EMAIL TEXT, $COL_PICTURE BLOB)")
        db!!.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }

    //CRUD
    fun addContact(contact: Contact){
        var bitmap = contact.image
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0,stream)
        val blob = stream.toByteArray()

        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_NAME, contact.name)
        values.put(COL_PHONE, contact.phone)
        values.put(COL_EMAIL, contact.email)
        values.put(COL_PICTURE, blob)

        db.insert(TABLE_NAME,null,values)
        db.close()
    }

    val getContacts:ArrayList<Contact>
        get(){
            val contactsOnTable = ArrayList<Contact>()
            val query = "SELECT * FROM $TABLE_NAME"
            val db = this.writableDatabase
            val cursor = db.rawQuery(query,null)
            if(cursor.moveToFirst()){
                do{
                    val blob = cursor.getBlob(cursor.getColumnIndex(COL_PICTURE))
                    val bitmap =  BitmapFactory.decodeByteArray(blob, 0,blob.size)
                    var contact = Contact(cursor.getString(cursor.getColumnIndex(COL_NAME)),cursor.getString(cursor.getColumnIndex(
                        COL_PHONE)),cursor.getString(cursor.getColumnIndex(COL_EMAIL)),bitmap)
                    contact.id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                    contactsOnTable.add(contact)
                }while (cursor.moveToNext())
            }
            db.close()
            return contactsOnTable
        }

    fun updateContact(contact: Contact):Int{
        var bitmap = contact.image
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0,stream)
        val blob = stream.toByteArray()

        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_NAME, contact.name)
        values.put(COL_PHONE, contact.phone)
        values.put(COL_EMAIL, contact.email)
        values.put(COL_PICTURE, blob)

        return db.update(TABLE_NAME,values,"$COL_ID=?", arrayOf(contact.id.toString()))
    }

    fun deleteContact(contact: Contact){

        val db = this.writableDatabase
        db.delete(TABLE_NAME,"$COL_ID=?", arrayOf(contact.id.toString()))
        db.close()
    }
}