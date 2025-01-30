package com.example.todonotes.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todonotes.Dependencies
import com.example.todonotes.repositories.Note

class SearchNotesViewModel : ViewModel()
{
    var searchPhrase = MutableLiveData<String>()
    var foundNotesList = MutableLiveData<MutableList<Note>>()

    init {
        foundNotesList.value = mutableListOf()
        searchPhrase.value = ""
        searchPhrase.observeForever(Observer {
            foundNotesList.value!!.clear()
            Dependencies.noteDao.getNoteByTitle('%'+searchPhrase.value!!+'%').mapTo(foundNotesList.value!!){note: Note -> note }
        })
    }

    class Factory() : ViewModelProvider.Factory
    {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchNotesViewModel() as T
        }
    }
}