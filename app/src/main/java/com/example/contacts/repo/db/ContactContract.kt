package com.example.contacts.repo.db

import android.provider.BaseColumns

object ContactContract {
    const val DB_NAME = "ContactsDB"
    const val DB_VERSION = 1

    object ContactEntry : BaseColumns {
        const val DB_TABLE = "contacts"
        const val COLUMN_ID = "id"
        const val COLUMN_FIRST_NAME = "first_name"
        const val COLUMN_LAST_NAME = "last_name"
        const val COLUMN_PHONE_NUMBER = "phone_number"
        const val COLUMN_EMAIL_ADDRESS = "email_address"
        const val COLUMN_ADDRESS = "address"
    }
}