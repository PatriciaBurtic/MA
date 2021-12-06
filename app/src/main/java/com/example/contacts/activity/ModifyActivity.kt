package com.example.contacts.activity

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.contacts.databinding.ModifyActivityBinding
import com.example.contacts.domain.Contact
import com.example.contacts.repo.ContactsRepoInterface
import com.example.contacts.repo.db.ContactContract
import com.example.contacts.repo.db.ContactDbManager
import com.example.contacts.toast
import com.example.contacts.utils.ApplicationUtils

class ModifyActivity : AppCompatActivity() {
    private lateinit var bindingModify: ModifyActivityBinding
    private val contactsRepo: ContactsRepoInterface =
        ApplicationUtils().getInstanceContactsRepository()!!


    companion object {
        const val CONTACT_POSITION = "POSITION"
        const val CONTACT_ID = "ID"
        const val CONTACT_FIRST_NAME = "FIRST_NAME"
        const val CONTACT_LAST_NAME = "LAST_NAME"
        const val CONTACT_PHONE = "PHONE"
        const val CONTACT_EMAIL = "EMAIL"
        const val CONTACT_ADDRESS = "ADDRESS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingModify = ModifyActivityBinding.inflate(layoutInflater)
        val view = bindingModify.root
        var id: Int = -1
        var position: Int = -1
        setContentView(view)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            bindingModify.firstName.setText(bundle.getString(CONTACT_FIRST_NAME))
            bindingModify.lastName.setText(bundle.getString(CONTACT_LAST_NAME))
            bindingModify.address.setText(bundle.getString(CONTACT_ADDRESS))
            bindingModify.email.setText(bundle.getString(CONTACT_EMAIL))
            bindingModify.phoneNumber.setText(bundle.getString(CONTACT_PHONE))
            id = bundle.getInt(CONTACT_ID)
            position = bundle.getInt(CONTACT_POSITION)
        }
        bindingModify.modifyButton.setOnClickListener {
            val dbManager = ContactDbManager(this)

            val firstName = bindingModify.firstName.text.toString()
            val lastName = bindingModify.lastName.text.toString()
            val address = bindingModify.address.text.toString()
            val email = bindingModify.email.text.toString()
            val phone = bindingModify.phoneNumber.text.toString()

            val values = ContentValues().apply {
                put(ContactContract.ContactEntry.COLUMN_FIRST_NAME, firstName)
                put(ContactContract.ContactEntry.COLUMN_LAST_NAME, lastName)
                put(ContactContract.ContactEntry.COLUMN_EMAIL_ADDRESS, email)
                put(ContactContract.ContactEntry.COLUMN_ADDRESS, address)
                put(ContactContract.ContactEntry.COLUMN_PHONE_NUMBER, phone)
            }
            val selectionArs = arrayOf(id.toString())
            dbManager.update(
                values,
                "${ContactContract.ContactEntry.COLUMN_ID}=?", selectionArs
            )

            val contact = Contact(id, firstName, lastName, phone, email, address)

            contactsRepo.modify(contact)

            val response = Intent()
            response.putExtra(CONTACT_POSITION, position)
            response.putExtra(CONTACT_ID, id)
            response.putExtra(CONTACT_FIRST_NAME, contact.firstName)
            response.putExtra(CONTACT_LAST_NAME, contact.lastName)
            response.putExtra(CONTACT_ADDRESS, contact.address)
            response.putExtra(CONTACT_EMAIL, contact.emailAddress)
            response.putExtra(CONTACT_PHONE, contact.phoneNumber)
            setResult(Activity.RESULT_OK, response)
            toast("This contact has been modified successfully!")
            finish()
        }
    }
}