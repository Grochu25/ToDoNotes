package com.example.todonotes.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todonotes.Dependencies
import com.example.todonotes.repositories.Note
import com.example.todonotes.repositories.Priority

class NotesMainViewModel : ViewModel()
{
    private var _highPriorityItems = MutableLiveData<List<Note>>()
    private var _mediumPriorityItems = MutableLiveData<List<Note>>()
    private var _lowPriorityItems = MutableLiveData<List<Note>>()
    val highPriorityItems = _highPriorityItems
    val mediumPriorityItems = _mediumPriorityItems
    val lowPriorityItems = _lowPriorityItems

    init {
        reloadDataFromDB()
    }

    fun reloadDataFromDB()
    {
        _highPriorityItems.value = Dependencies.noteDao.getAllByPriority(Priority.WYSOKA)
        _mediumPriorityItems.value = Dependencies.noteDao.getAllByPriority(Priority.SREDNIA)
        _lowPriorityItems.value = Dependencies.noteDao.getAllByPriority(Priority.NISKA)
    }

    class Factory() : ViewModelProvider.Factory
    {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NotesMainViewModel() as T
        }
    }
}