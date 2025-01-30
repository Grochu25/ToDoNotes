package com.example.todonotes.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todonotes.Dependencies
import com.example.todonotes.repositories.Category

class AllCategoriesViewModel : ViewModel()
{
    var categories = MutableLiveData<MutableList<Category>>()
    var categoryItemsCount = MutableLiveData<Map<String, Int>>()

    val delete: (Category) -> Unit = {
        Dependencies.categoryDao.deleteNotesFromCategory(it.categoryId)
        Dependencies.categoryDao.deleteCategory(it.categoryId)
        reloadDataFromDB()
    }

    init {
        categories.value = mutableListOf()
        categoryItemsCount.value = Dependencies.noteDao.getAllByCategories().map{ it.category.name!! to it.notes.size}.toMap()
        reloadDataFromDB()
    }

    fun reloadDataFromDB()
    {
        categories.value?.clear()
        Dependencies.categoryDao.getAll().mapTo(categories.value!!){it}
    }

    class Factory() : ViewModelProvider.Factory
    {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AllCategoriesViewModel() as T
        }
    }
}