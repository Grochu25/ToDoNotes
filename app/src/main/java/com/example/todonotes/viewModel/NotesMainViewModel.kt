package com.example.todonotes.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todonotes.Dependencies
import com.example.todonotes.repositories.Category
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

    var categories: MutableList<Category>
    var choosenCategory: Int = -1

    var sortType: SortType = SortType.DATE_ASC
    private var allNotesVisible: Boolean = true

    init {
        categories = mutableListOf()
        reloadDataFromDB()
    }

    fun reloadDataFromDB()
    {
        reloadDataFromDB(sortType)
    }

    fun toggleAllNotesVisibility() {
        allNotesVisible = !allNotesVisible
        reloadDataFromDB()
    }

    fun reloadDataFromDB(sortType: SortType)
    {
        categories.clear()
        Dependencies.categoryDao.getAll().mapTo(categories){it}

        when(sortType)
        {
            SortType.DATE_DESC -> {
                _highPriorityItems.value = Dependencies.noteDao.getAllWithPrioOrderedByFavDateDESC(Priority.WYSOKA).filter{(choosenCategory >= 0 && it.categoryId == choosenCategory) || choosenCategory<0}.toList()
                _mediumPriorityItems.value = Dependencies.noteDao.getAllWithPrioOrderedByFavDateDESC(Priority.SREDNIA).filter{(choosenCategory >= 0 && it.categoryId == choosenCategory) || choosenCategory<0}.toList()
                _lowPriorityItems.value = Dependencies.noteDao.getAllWithPrioOrderedByFavDateDESC(Priority.NISKA).filter{(choosenCategory >= 0 && it.categoryId == choosenCategory) || choosenCategory<0}.toList()
            }
            SortType.DATE_ASC -> {
                _highPriorityItems.value = Dependencies.noteDao.getAllWithPrioOrderedByFavDateASC(Priority.WYSOKA).filter{(choosenCategory >= 0 && it.categoryId == choosenCategory) || choosenCategory<0}.toList()
                _mediumPriorityItems.value = Dependencies.noteDao.getAllWithPrioOrderedByFavDateASC(Priority.SREDNIA).filter{(choosenCategory >= 0 && it.categoryId == choosenCategory) || choosenCategory<0}.toList()
                _lowPriorityItems.value = Dependencies.noteDao.getAllWithPrioOrderedByFavDateASC(Priority.NISKA).filter{(choosenCategory >= 0 && it.categoryId == choosenCategory) || choosenCategory<0}.toList()
            }
            SortType.NAME_ASC -> {
                _highPriorityItems.value = Dependencies.noteDao.getAllWithPrioOrderedByFavTitleASC(Priority.WYSOKA).filter{(choosenCategory >= 0 && it.categoryId == choosenCategory) || choosenCategory<0}.toList()
                _mediumPriorityItems.value = Dependencies.noteDao.getAllWithPrioOrderedByFavTitleASC(Priority.SREDNIA).filter{(choosenCategory >= 0 && it.categoryId == choosenCategory) || choosenCategory<0}.toList()
                _lowPriorityItems.value = Dependencies.noteDao.getAllWithPrioOrderedByFavTitleASC(Priority.NISKA).filter{(choosenCategory >= 0 && it.categoryId == choosenCategory) || choosenCategory<0}.toList()
            }
            SortType.NAME_DESC -> {
                _highPriorityItems.value = Dependencies.noteDao.getAllWithPrioOrderedByFavTitleDESC(Priority.WYSOKA).filter{(choosenCategory >= 0 && it.categoryId == choosenCategory) || choosenCategory<0}.toList()
                _mediumPriorityItems.value = Dependencies.noteDao.getAllWithPrioOrderedByFavTitleDESC(Priority.SREDNIA).filter{(choosenCategory >= 0 && it.categoryId == choosenCategory) || choosenCategory<0}.toList()
                _lowPriorityItems.value = Dependencies.noteDao.getAllWithPrioOrderedByFavTitleDESC(Priority.NISKA).filter{(choosenCategory >= 0 && it.categoryId == choosenCategory) || choosenCategory<0}.toList()
            }
            else -> {
                _highPriorityItems.value = Dependencies.noteDao.getAllByPriority(Priority.WYSOKA).filter{(choosenCategory >= 0 && it.categoryId == choosenCategory) || choosenCategory<0}.toList()
                _mediumPriorityItems.value = Dependencies.noteDao.getAllByPriority(Priority.SREDNIA).filter{(choosenCategory >= 0 && it.categoryId == choosenCategory) || choosenCategory<0}.toList()
                _lowPriorityItems.value = Dependencies.noteDao.getAllByPriority(Priority.NISKA).filter{(choosenCategory >= 0 && it.categoryId == choosenCategory) || choosenCategory<0}.toList()
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