package com.example.contacts.repo

import com.example.contacts.domain.Contact

class ContactsRepoMock : ContactsRepoInterface {
    private var contacts: ArrayList<Contact> = ArrayList(
        listOf(
            Contact(
                1,
                "Jane",
                "Doe",
                "0729899899",
                "janedoe@gmail.com",
                "Hotel Street no 1"
            ),
            Contact(
                2,
                "John",
                "Doe",
                "0727899899",
                "johndoe@gmail.com",
                "Hotel Street 2"
            ),
            Contact(
                3,
                "William",
                "Smith",
                "0728990990",
                "wsmith@gmail.com",
                "Hotel Street 3"
            ),
            Contact(
                4,
                "Luke",
                "Donald",
                "0728943990",
                "luked@gmail.com",
                "Hotel Street 4"
            ),
            Contact(
                5,
                "Mira",
                "Donald",
                "0776990990",
                "mira_donald@gmail.com",
                "Hotel Street 5"
            ),
            Contact(
                6,
                "Hanna",
                "(From Work)",
                "0728990967",
                "hanna1123@gmail.com",
                "Hotel Street 6"
            ),
            Contact(
                7,
                "Donald",
                "Duck",
                "0728990976",
                "dd99@gmail.com",
                "Hotel Street 7"
            ),
            Contact(
                8,
                "Lisa",
                "Doe",
                "0728990966",
                "lisa.doe@gmail.com",
                "Hotel Street 8"
            )
        )
    )

    override fun findAll(): List<Contact> {
        return contacts;
    }

    override fun modify(position: Int, contact: Contact) {
        contacts[position] = contact
    }

    override fun add(contact: Contact): Int {
        contact.id = getNextId()
        contacts.add(contact)
        return contact.id
    }

    override fun removeAt(position: Int) {
        contacts.removeAt(position)
    }

    private fun getNextId(): Int {
        var max = 0;
        contacts.forEach() { x ->
            if (x.id > max) {
                max = x.id
            }
        }
        return max + 1
    }
}