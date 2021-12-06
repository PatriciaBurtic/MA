package com.example.contacts.repo.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class ContactDbManager(context: Context) {
    private val dbHelper: Helper = Helper(context)
    private val db: SQLiteDatabase by lazy { dbHelper.writableDatabase }

    fun insert(values: ContentValues): Long {
        return db.insert(ContactContract.ContactEntry.DB_TABLE, "", values)
    }

    fun queryAll(): Cursor {
        return db.rawQuery("select * from ${ContactContract.ContactEntry.DB_TABLE}", null)
    }

    fun delete(selection: String, selectionArgs: Array<String>): Int {
        return db.delete(ContactContract.ContactEntry.DB_TABLE, selection, selectionArgs)
    }

    fun update(values: ContentValues, selection: String, selectionArgs: Array<String>): Int {
        return db.update(ContactContract.ContactEntry.DB_TABLE, values, selection, selectionArgs)
    }

    fun close() {
        db.close()
    }
}