package com.example.contacts.utils

import com.example.contacts.repo.ContactsRepoInterface
import com.example.contacts.repo.ContactsRepoMock
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
            contactsRepo = ContactsRepoMock()
        }
        return contactsRepo
    }

    @Provides
    @Singleton
    fun provideContactRepository(repository: ContactsRepoMock) : ContactsRepoInterface {
        return ContactsRepoMock()
    }
}