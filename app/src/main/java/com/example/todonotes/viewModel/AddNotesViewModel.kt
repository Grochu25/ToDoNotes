package com.example.todonotes.viewModel

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todonotes.Dependencies
import com.example.todonotes.repositories.Note
import com.example.todonotes.repositories.Priority
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


class AddNotesViewModel : ViewModel()
{
    var id: Int = -1
    var title = MutableLiveData<String>()
    var description = MutableLiveData<String>()
    var date = MutableLiveData<String?>()
    var time = MutableLiveData<String?>()
    var category = MutableLiveData<Int?>()
    var priority = MutableLiveData<Int?>()

    var categories: MutableList<String> = mutableListOf()
    var priorities: MutableList<String> = mutableListOf()

    init{
        var noteList: List<Note> = Dependencies.noteDao.getAll()
        id = if (noteList.isEmpty()) 0 else (noteList.sortedBy { it.noteId }.last().noteId +1)
        Dependencies.categoryDao.getAll()
            .filter { it.categoryId != -1 }
            .sortedBy { it.name }
            .mapTo(categories) { it.name!! }
        Priority.values().mapTo(priorities){it.name}

        title.value = ""
        description.value = ""
        date.value = ""
        time.value = ""
        category.value = 0
        priority.value = 0
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveNoteToDatabase()
    {
        if(date.value!!.get(3).equals(' ') && date.value!!.get(5).equals(',') )
            date.value = date.value!!.substring(0,4)+'0'+date.value!!.substring(4)

        if(Dependencies.noteDao.getNoteById(id) == null)
            Dependencies.noteDao.insertAll(
                Note(id, title.value, description.value,
                    LocalDateTime.parse((date!!.value+" : "+time!!.value+" +0100"), DateTimeFormatter.ofPattern("MMM dd, yyyy : HH:mm Z")),
                    Priority.values()[priority.value!!],
                    Dependencies.categoryDao.getCategoryByName(categories[category.value!!])!!.categoryId,
                    false
                )
            )
    }

    @SuppressLint("NewApi")
    fun getInstant(): Instant
    {
        if(!date.value.equals("") && !time.value.equals("") )
        {
            return LocalDateTime.parse((date!!.value+" : "+time!!.value+" +0100"),
                DateTimeFormatter.ofPattern("MMM dd, yyyy : HH:mm Z")).toInstant(ZoneOffset.from(
                ZonedDateTime.now()))
        }
        return Instant.now()
    }

    class Factory() : ViewModelProvider.Factory
    {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddNotesViewModel() as T
        }
    }
}