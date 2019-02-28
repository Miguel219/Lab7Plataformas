package com.example.lab7plataformas.adapter

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.example.lab7plataformas.*
import kotlinx.android.synthetic.main.contact_item.view.*

class ContactAdapter (var context: Context,var list: ArrayList<Contact>): RecyclerView.Adapter<ContactAdapter.ViewHolder>(){

    var myContext: Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.contact_item,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ContactAdapter.ViewHolder, position: Int) {
        holder.bindItems(list[position])
        holder.itemView.setOnClickListener {
            MyApplication.selectedPosition = position
            val intent: Intent = Intent(  myContext, Main3Activity::class.java)
            myContext.startActivity(intent)
        }

        holder.itemView.setOnLongClickListener(object: AdapterView.OnItemLongClickListener, View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                var conexionSQLite = ConexionSQLite(myContext)
                conexionSQLite.deleteContact(MyApplication.contactArray[position])
                //Se actualizan los datos de los contactos
                MyApplication.contactArray = conexionSQLite.getContacts
                var adapter = ArrayAdapter(myContext, R.layout.contact_item, MyApplication.contactArray)

                Toast.makeText(myContext, "Se ha eliminado el contacto", Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {
                return true
            }
        })
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        fun bindItems(data: Contact){
            val title:TextView=itemView.findViewById(R.id.text_view_title)
            val description:TextView=itemView.findViewById(R.id.text_view_description)

            title.text=data.name
            description.text=data.phone
        }
    }

}