package com.example.todonotes.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todonotes.Dependencies
import com.example.todonotes.repositories.Category
import com.example.todonotes.repositories.Note
import com.example.todonotes.repositories.Priority

class AllCategoriesViewModel : ViewModel()
{
    private var _categories = MutableLiveData<List<Category>>()
    val categories = _categories

    init {
        reloadDataFromDB()
    }

    fun reloadDataFromDB()
    {
        _categories.value = Dependencies.categoryDao.getAll()
    }

    class Factory() : ViewModelProvider.Factory
    {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NotesMainViewModel() as T
        }
    }
}