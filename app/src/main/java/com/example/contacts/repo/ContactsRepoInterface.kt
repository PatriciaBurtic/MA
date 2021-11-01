package com.example.contacts.repo

import com.example.contacts.domain.Contact

interface ContactsRepoInterface {
    fun findAll() : List<Contact>
    fun modify(position:Int, contact:Contact)
    fun add(contact: Contact):Int
    fun removeAt(position: Int)
}