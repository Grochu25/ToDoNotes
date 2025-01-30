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
    enum class SortType{DATE_ASC,DATE_DESC,NAME_ASC,NAME_DESC}

    private var _highPriorityItems = MutableLiveData<List<Note>>()
    private var _mediumPriorityItems = MutableLiveData<List<Note>>()
    private var _lowPriorityItems = MutableLiveData<List<Note>>()
    val highPriorityItems = _highPriorityItems
    val mediumPriorityItems = _mediumPriorityItems
    val lowPriorityItems = _lowPriorityItems

    var sortType: SortType = SortType.DATE_ASC

    init {
        reloadDataFromDB()
    }

    fun reloadDataFromDB()
    {
        reloadDataFromDB(sortType)
    }

    fun reloadDataFromDB(sortType: SortType)
    {
        when(sortType)
        {
            SortType.DATE_DESC -> {
                _highPriorityItems.value = Dependencies.noteDao.getAllWithOrderedByFavDateDESC(Priority.WYSOKA)
                _mediumPriorityItems.value = Dependencies.noteDao.getAllWithOrderedByFavDateDESC(Priority.SREDNIA)
                _lowPriorityItems.value = Dependencies.noteDao.getAllWithOrderedByFavDateDESC(Priority.NISKA)
            }
            SortType.DATE_ASC -> {
                _highPriorityItems.value = Dependencies.noteDao.getAllWithOrderedByFavDateASC(Priority.WYSOKA)
                _mediumPriorityItems.value = Dependencies.noteDao.getAllWithOrderedByFavDateASC(Priority.SREDNIA)
                _lowPriorityItems.value = Dependencies.noteDao.getAllWithOrderedByFavDateASC(Priority.NISKA)
            }
            SortType.NAME_ASC -> {
                _highPriorityItems.value = Dependencies.noteDao.getAllWithOrderedByFavTitleASC(Priority.WYSOKA)
                _mediumPriorityItems.value = Dependencies.noteDao.getAllWithOrderedByFavTitleASC(Priority.SREDNIA)
                _lowPriorityItems.value = Dependencies.noteDao.getAllWithOrderedByFavTitleASC(Priority.NISKA)
            }
            SortType.NAME_DESC -> {
                _highPriorityItems.value = Dependencies.noteDao.getAllWithOrderedByFavTitleDESC(Priority.WYSOKA)
                _mediumPriorityItems.value = Dependencies.noteDao.getAllWithOrderedByFavTitleDESC(Priority.SREDNIA)
                _lowPriorityItems.value = Dependencies.noteDao.getAllWithOrderedByFavTitleDESC(Priority.NISKA)
            }
            else -> {
                _highPriorityItems.value = Dependencies.noteDao.getAllByPriority(Priority.WYSOKA)
                _mediumPriorityItems.value = Dependencies.noteDao.getAllByPriority(Priority.SREDNIA)
                _lowPriorityItems.value = Dependencies.noteDao.getAllByPriority(Priority.NISKA)
            }
        }

    }

    class Factory() : ViewModelProvider.Factory
    {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NotesMainViewModel() as T
        }
    }
}