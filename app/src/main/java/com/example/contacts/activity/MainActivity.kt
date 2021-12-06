package com.example.contacts.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.databinding.ActivityMainBinding
import com.example.contacts.databinding.ContactCardBinding
import com.example.contacts.domain.Contact
import com.example.contacts.repo.ContactsRepoInterface
import com.example.contacts.repo.db.ContactContract
import com.example.contacts.repo.db.ContactDbManager
import com.example.contacts.utils.ApplicationUtils


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val repo: ContactsRepoInterface = ApplicationUtils().getInstanceContactsRepository()!!
    private val adapter = MainAdapter(this)
    private val dbManager = ContactDbManager(this)

    private val modifyContactLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (intent != null) {
                    val intentBundle = intent.extras
                    if (intentBundle != null) {
                        val position = intentBundle.getInt(ModifyActivity.CONTACT_POSITION)
                        val id = intentBundle.getInt(ModifyActivity.CONTACT_ID)
                        val firstName = intentBundle.getString(ModifyActivity.CONTACT_FIRST_NAME)!!
                        val lastName = intentBundle.getString(ModifyActivity.CONTACT_LAST_NAME)!!
                        val phone = intentBundle.getString(ModifyActivity.CONTACT_PHONE)!!
                        val email = intentBundle.getString(ModifyActivity.CONTACT_EMAIL)!!
                        val address = intentBundle.getString(ModifyActivity.CONTACT_ADDRESS)!!

                        adapter.modifyContactInList(
                            Contact(
                                id,
                                firstName,
                                lastName,
                                phone,
                                email,
                                address
                            ), position
                        )
                    }
                }
            }
        }

    private val addContactLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (intent != null) {
                    val intentBundle = intent.extras
                    if (intentBundle != null) {
                        val id = intentBundle.getInt(AddActivity.CONTACT_ID)
                        val firstName = intentBundle.getString(AddActivity.CONTACT_FIRST_NAME)!!
                        val lastName = intentBundle.getString(AddActivity.CONTACT_LAST_NAME)!!
                        val phone = intentBundle.getString(AddActivity.CONTACT_PHONE)!!
                        val email = intentBundle.getString(AddActivity.CONTACT_EMAIL)!!
                        val address = intentBundle.getString(AddActivity.CONTACT_ADDRESS)!!

                        adapter.addContactInList(
                            Contact(
                                id,
                                firstName,
                                lastName,
                                phone,
                                email,
                                address
                            )
                        )
                    }
                }
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.recyclerView.adapter = adapter
        var list = loadQueryAll()
        adapter.setContactsList(list)
        repo.setContacts(list)
        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            addContactLauncher.launch(intent)
        }
    }

    override fun onDestroy() {
        dbManager.close()
        super.onDestroy()
    }

    fun loadQueryAll(): MutableList<Contact> {
        val cursor = dbManager.queryAll()
        var listContacts = mutableListOf<Contact>();
        if (cursor.moveToFirst()) {
            do {
                val idColumnIndex = cursor.getColumnIndex(ContactContract.ContactEntry.COLUMN_ID)
                val firstNameColumnIndex =
                    cursor.getColumnIndex(ContactContract.ContactEntry.COLUMN_FIRST_NAME)
                val lastNameColumnIndex =
                    cursor.getColumnIndex(ContactContract.ContactEntry.COLUMN_LAST_NAME)
                val emailAddressColumnIndex =
                    cursor.getColumnIndex(ContactContract.ContactEntry.COLUMN_EMAIL_ADDRESS)
                val addressColumnIndex =
                    cursor.getColumnIndex(ContactContract.ContactEntry.COLUMN_ADDRESS)
                val phoneNumberColumnIndex =
                    cursor.getColumnIndex(ContactContract.ContactEntry.COLUMN_PHONE_NUMBER)
                val id = cursor.getInt(idColumnIndex)
                val firstName = cursor.getString(firstNameColumnIndex)
                val lastName = cursor.getString(lastNameColumnIndex)
                val emailAddress = cursor.getString(emailAddressColumnIndex)
                val address = cursor.getString(addressColumnIndex)
                val phoneNumber = cursor.getString(phoneNumberColumnIndex)
                listContacts.add(
                    Contact(
                        id,
                        firstName,
                        lastName,
                        phoneNumber,
                        emailAddress,
                        address
                    )
                )
            } while (cursor.moveToNext())
        }
        return listContacts;
    }


    inner class MainAdapter(context: Context) : RecyclerView.Adapter<MainViewHolder>() {
        var contacts = mutableListOf<Contact>()
        private var context: Context? = context

        @SuppressWarnings("NotifyDataSetChanged")
        fun setContactsList(contacts: List<Contact>) {
            this.contacts = contacts.toMutableList()
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ContactCardBinding.inflate(inflater, parent, false)
            return MainViewHolder(binding)

        }

        @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val contact = contacts[position]
            holder.binding.cardFullName.text = contact.firstName + " " + contact.lastName
            holder.binding.cardPhoneNumber.text = contact.phoneNumber
            holder.binding.deleteButton.setOnClickListener {
                val dbManager = ContactDbManager(this.context!!)
                val selectionArgs = arrayOf(contact.id.toString())
                dbManager.delete(
                    "${ContactContract.ContactEntry.COLUMN_ID}=?",
                    selectionArgs
                )
                contacts.remove(contact)
                repo.removeById(contact.id)
                notifyDataSetChanged()
            }
            holder.binding.cardId.setOnClickListener() {
                val intent = Intent(this@MainActivity, ModifyActivity::class.java)
                intent.putExtra(ModifyActivity.CONTACT_POSITION, position)
                intent.putExtra(ModifyActivity.CONTACT_ID, contact.id)
                intent.putExtra(ModifyActivity.CONTACT_FIRST_NAME, contact.firstName)
                intent.putExtra(ModifyActivity.CONTACT_LAST_NAME, contact.lastName)
                intent.putExtra(ModifyActivity.CONTACT_ADDRESS, contact.address)
                intent.putExtra(ModifyActivity.CONTACT_EMAIL, contact.emailAddress)
                intent.putExtra(ModifyActivity.CONTACT_PHONE, contact.phoneNumber)
                modifyContactLauncher.launch(intent)
            }
        }

        override fun getItemCount(): Int {
            return contacts.size
        }

        @SuppressWarnings("NotifyDataSetChanged")
        fun modifyContactInList(contact: Contact, position: Int) {
            contacts[position] = contact
            notifyDataSetChanged()

        }

        @SuppressWarnings("NotifyDataSetChanged")
        fun addContactInList(contact: Contact) {
            contacts.add(contact)
            notifyDataSetChanged()
        }
    }

    class MainViewHolder(val binding: ContactCardBinding) : RecyclerView.ViewHolder(binding.root)
}