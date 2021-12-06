package com.example.contacts.repo.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

private const val SQL_CREATE_ENTRIES =
    """CREATE TABLE IF NOT EXISTS ${ContactContract.ContactEntry.DB_TABLE} (
            ${ContactContract.ContactEntry.COLUMN_ID} INTEGER PRIMARY KEY,
            ${ContactContract.ContactEntry.COLUMN_FIRST_NAME} TEXT,
            ${ContactContract.ContactEntry.COLUMN_LAST_NAME} TEXT,
            ${ContactContract.ContactEntry.COLUMN_PHONE_NUMBER} TEXT,
            ${ContactContract.ContactEntry.COLUMN_EMAIL_ADDRESS} TEXT,
            ${ContactContract.ContactEntry.COLUMN_ADDRESS} TEXT)
        """
private const val SQL_DELETE_ENTRIES =
    "DROP TABLE IF EXISTS ${ContactContract.ContactEntry.DB_TABLE}"

class Helper
    (private var context: Context) :
    SQLiteOpenHelper(
        context,
        ContactContract.DB_NAME,
        null,
        ContactContract.DB_VERSION
    ) {

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(SQL_CREATE_ENTRIES)
        context.toast(" database is created")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(SQL_DELETE_ENTRIES)
    }
}

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
