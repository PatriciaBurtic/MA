package com.example.contacts.domain

data class Contact(
    var id: Int,
    var firstName: String,
    var lastName: String,
    var phoneNumber: String,
    var emailAddress: String,
    var address: String
) {
    constructor(firstName: String, lastName: String, phoneNumber: String, emailAddress: String, address: String):
            this(0, firstName, lastName, phoneNumber, emailAddress, address)
}