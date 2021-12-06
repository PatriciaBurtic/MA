package com.example.contacts.repo

import com.example.contacts.domain.Contact

interface ContactsRepoInterface {
    fun findAll(): List<Contact>
    fun modify(contact: Contact)
    fun add(contact: Contact): Int
    fun removeById(id: Int)
    fun removeAt(position: Int)
    fun setContacts(list: List<Contact>)
}