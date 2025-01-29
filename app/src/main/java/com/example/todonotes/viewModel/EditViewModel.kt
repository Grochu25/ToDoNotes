package com.example.todonotes.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todonotes.Dependencies
import com.example.todonotes.repositories.Note
import com.example.todonotes.repositories.Priority
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class EditViewModel(noteId: Int) : ViewModel()
{
    var note: Note
    val id = noteId
    var title = MutableLiveData<String>()
    var description = MutableLiveData<String>()
    var date = MutableLiveData<String?>()
    var time = MutableLiveData<String?>()
    var category = MutableLiveData<Int?>()
    var priority = MutableLiveData<Int?>()

    var categories: MutableList<String> = mutableListOf()
    var priorities: MutableList<String> = mutableListOf()

    init {
        note = Dependencies.noteDao.getNoteById(noteId)
        title.value = note.title
        description.value = note.desc
        date.value = note.date?.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
        time.value = note.date?.format(DateTimeFormatter.ofPattern("HH:mm"))
        Dependencies.categoryDao.getAll().mapTo(categories){ it.name!! }
        Priority.values().mapTo(priorities){it.name}

        category.value = categories.indexOf(Dependencies.categoryDao.getCategoryById(note.categoryId).name)
        priority.value = note.priority.ordinal
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveChangesToDatabase()
    {
            Dependencies.noteDao.update(
                Note(id, title.value, description.value,
                    LocalDateTime.parse((date!!.value+" : "+time!!.value+" +0100"), DateTimeFormatter.ofPattern("MMM dd, yyyy : HH:mm Z")),
                    Priority.values()[priority.value!!],
                    Dependencies.categoryDao.getCategoryByName(categories[category.value!!]).categoryId,
                    note.isFavorite
                )
            )
        var result: Note? = null
        do {
            result = Dependencies.noteDao.getNoteById(id)
        }while(result == null)
    }

    fun deleteNote()
    {
        Dependencies.noteDao.delete(note)
    }

    class Factory(val noteId: Int) : ViewModelProvider.Factory
    {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EditViewModel(noteId) as T
        }
    }
}