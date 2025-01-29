package com.example.todonotes

import com.example.todonotes.repositories.CategoryDao
import com.example.todonotes.repositories.NoteDao

class Dependencies {
    companion object {
        lateinit var categoryDao: CategoryDao
        lateinit var noteDao: NoteDao
    }
}