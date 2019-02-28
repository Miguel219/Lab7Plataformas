package com.example.lab7plataformas.data

import android.app.Application
import android.arch.lifecycle.LiveData
import android.provider.ContactsContract
import android.os.AsyncTask

class ContactRepository(application: Application) {

    private var contactDao: ContactDao

    private var allContacts: LiveData<List<Contact>>

    init {
        val database: ContactDatabase = ContactDatabase.getInstance(
            application.applicationContext
        )!!
        contactDao = database.contactDao()
        allContacts = contactDao.getAllContacts()
    }

    fun insert(contact: Contact) {
        val insertContactAsyncTask = InsertContactAsyncTask(contactDao).execute(contact)
    }

    fun update(contact: Contact) {
        val updateContactAsyncTask = UpdateContactAsyncTask(contactDao).execute(contact)
    }


    fun delete(contact: Contact) {
        val deleteContactAsyncTask = DeleteContactAsyncTask(contactDao).execute(contact)
    }

    fun deleteAllContacts() {
        val deleteAllContactsAsyncTask = DeleteAllContactsAsyncTask(
            contactDao
        ).execute()
    }

    fun getAllContacts(): LiveData<List<Contact>> {
        return allContacts
    }


    companion object {
        private class InsertContactAsyncTask(contactDao: ContactDao) : AsyncTask<Contact, Unit, Unit>() {
            val noteDao = contactDao

            override fun doInBackground(vararg p0: Contact?) {
                noteDao.insert(p0[0]!!)
            }
        }

        private class UpdateContactAsyncTask(contactDao: ContactDao) : AsyncTask<Contact, Unit, Unit>() {
            val noteDao = contactDao

            override fun doInBackground(vararg p0: Contact?) {
                noteDao.update(p0[0]!!)
            }
        }

        private class DeleteContactAsyncTask(contactDao: ContactDao) : AsyncTask<Contact, Unit, Unit>() {
            val noteDao = contactDao

            override fun doInBackground(vararg p0: Contact?) {
                noteDao.delete(p0[0]!!)
            }
        }

        private class DeleteAllContactsAsyncTask(contactDao: ContactDao) : AsyncTask<Unit, Unit, Unit>() {
            val noteDao = contactDao

            override fun doInBackground(vararg p0: Unit?) {
                noteDao.deleteAllContacts()
            }
        }
    }
}