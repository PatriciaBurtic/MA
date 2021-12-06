package com.example.contacts.utils

import com.example.contacts.repo.ContactsRepoInterface
import com.example.contacts.repo.ContactsRepoInMemory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationUtils {
    companion object {
        private var contactsRepo: ContactsRepoInterface ?= null
    }

    fun getInstanceContactsRepository() : ContactsRepoInterface ? {
        if(contactsRepo == null) {
            contactsRepo = ContactsRepoInMemory()
        }
        return contactsRepo
    }

    @Provides
    @Singleton
    fun provideContactRepository(repository: ContactsRepoInMemory) : ContactsRepoInterface {
        return ContactsRepoInMemory()
    }
}