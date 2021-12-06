package com.example.contacts.repo

import com.example.contacts.domain.Contact

class ContactsRepoInMemory : ContactsRepoInterface {
    private var contacts: ArrayList<Contact> = ArrayList()

    override fun findAll(): List<Contact> {
        return contacts;
    }

    override fun modify(contact: Contact) {
        var i = 0
        for (c in contacts) {
            if (c.id == contact.id)
                contacts[i] = contact
            i++
        }
    }

    override fun add(contact: Contact): Int {
        contacts.add(contact)
        return contact.id
    }

    override fun removeById(id: Int) {
        var i = 0
        for (c in contacts) {
            if (c.id == id) {
                contacts.removeAt(i)
                break
            }
            i++
        }
    }

    override fun removeAt(position: Int) {
        contacts.removeAt(position)
    }

    override fun setContacts(list: List<Contact>) {
        contacts = list as ArrayList<Contact>
    }
}