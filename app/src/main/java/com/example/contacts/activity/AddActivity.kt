package com.example.contacts.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.contacts.databinding.AddActivityBinding
import com.example.contacts.domain.Contact
import com.example.contacts.repo.ContactsRepoInterface
import com.example.contacts.toast
import com.example.contacts.utils.ApplicationUtils

class AddActivity : AppCompatActivity() {
    private lateinit var bindingAdd: AddActivityBinding
    private val contactsRepo: ContactsRepoInterface = ApplicationUtils().getInstanceContactsRepository()!!


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

        bindingAdd.saveButton.setOnClickListener{
            toast("Contact has been added successfully.")
            val firstName = bindingAdd.firstName.text.toString()
            val lastName = bindingAdd.lastName.text.toString()
            val address = bindingAdd.address.text.toString()
            val email = bindingAdd.email.text.toString()
            val phone = bindingAdd.phoneNumber.text.toString()


            val contact = Contact(firstName, lastName, phone, email, address)

            val id = contactsRepo.add(contact)

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

        bindingAdd.cancelButton.setOnClickListener{
            finish()
        }
    }
}