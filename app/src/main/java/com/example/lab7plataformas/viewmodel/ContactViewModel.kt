package com.example.lab7plataformas.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.lab7plataformas.data.Contact
import com.example.lab7plataformas.data.ContactRepository

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: ContactRepository =
        ContactRepository(application)
    private var allNotes: LiveData<List<Contact>> = repository.getAllContacts()

    fun insert(contact: Contact) {
        repository.insert(contact)
    }

    fun update(contact: Contact) {
        repository.update(contact)
    }

    fun delete(contact: Contact) {
        repository.delete(contact)
    }

    fun deleteAllNotes() {
        repository.deleteAllContacts()
    }

    fun getAllNotes(): LiveData<List<Contact>> {
        return allNotes
    }
}