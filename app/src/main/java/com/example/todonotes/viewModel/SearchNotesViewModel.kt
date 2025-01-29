package com.example.todonotes.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.todonotes.Dependencies
import com.example.todonotes.repositories.Note

class SearchNotesViewModel : ViewModel()
{
    var searchPhrase = MutableLiveData<String>()
    var foundNotesList = MutableLiveData<List<Note>>()

    init {
        searchPhrase.value = ""
        searchPhrase.observeForever(Observer {
            foundNotesList.value = Dependencies.noteDao.getNoteByTitle(searchPhrase.value ?:"")
        })
    }
}