package com.example.contacts.activity

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.contacts.databinding.AddActivityBinding
import com.example.contacts.domain.Contact
import com.example.contacts.repo.ContactsRepoInterface
import com.example.contacts.repo.db.ContactContract
import com.example.contacts.repo.db.ContactDbManager
import com.example.contacts.toast
import com.example.contacts.utils.ApplicationUtils

class AddActivity : AppCompatActivity() {
    private lateinit var bindingAdd: AddActivityBinding
    private val contactsRepo: ContactsRepoInterface =
        ApplicationUtils().getInstanceContactsRepository()!!


    companion object {
        const val CONTACT_ID = "ID"
        const val CONTACT_FIRST_NAME = "FIRST_NAME"
        const val CONTACT_LAST_NAME = "LAST_NAME"
        const val CONTACT_PHONE = "PHONE"
        const val CONTACT_EMAIL = "EMAIL"
        const val CONTACT_ADDRESS = "ADDRESS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingAdd = AddActivityBinding.inflate(layoutInflater)
        val view = bindingAdd.root
        setContentView(view)

        bindingAdd.saveButton.setOnClickListener {
            val dbManager = ContactDbManager(this)
            toast("Contact has been added successfully.")
            val firstName = bindingAdd.firstName.text.toString()
            val lastName = bindingAdd.lastName.text.toString()
            val address = bindingAdd.address.text.toString()
            val email = bindingAdd.email.text.toString()
            val phone = bindingAdd.phoneNumber.text.toString()

            val values = ContentValues().apply {
                put(ContactContract.ContactEntry.COLUMN_FIRST_NAME, firstName)
                put(ContactContract.ContactEntry.COLUMN_LAST_NAME, lastName)
                put(ContactContract.ContactEntry.COLUMN_EMAIL_ADDRESS, email)
                put(ContactContract.ContactEntry.COLUMN_ADDRESS, address)
                put(ContactContract.ContactEntry.COLUMN_PHONE_NUMBER, phone)
            }

            val id = dbManager.insert(values).toInt()

            val contact = Contact(id,firstName, lastName, phone, email, address)

            contactsRepo.add(contact)

            val response = Intent()
            response.putExtra(AddActivity.CONTACT_ID, id)
            response.putExtra(AddActivity.CONTACT_FIRST_NAME, contact.firstName)
            response.putExtra(AddActivity.CONTACT_LAST_NAME, contact.lastName)
            response.putExtra(AddActivity.CONTACT_ADDRESS, contact.address)
            response.putExtra(AddActivity.CONTACT_EMAIL, contact.emailAddress)
            response.putExtra(AddActivity.CONTACT_PHONE, contact.phoneNumber)
            setResult(Activity.RESULT_OK, response)
            finish()
        }

        bindingAdd.cancelButton.setOnClickListener {
            finish()
        }
    }
}