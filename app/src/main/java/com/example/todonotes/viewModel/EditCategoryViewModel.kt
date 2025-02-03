package com.example.todonotes.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todonotes.Dependencies
import com.example.todonotes.repositories.Category

class EditCategoryViewModel(val editedId: Int) : ViewModel()
{
    var category: Category
    var name = MutableLiveData<String>()
    var color = MutableLiveData<Int>()

    init {
        category = Dependencies.categoryDao.getCategoryById(editedId) ?: Category(-2,"",0)
        name.value = category.name
        color.value = category.color
    }

    fun updateInDatabase()
    {
        Dependencies.categoryDao.update(Category(category.categoryId, name.value!!, color.value!!))
    }

    fun canSaveChanges(): Boolean
    {
        if(Dependencies.categoryDao.getCategoryByName(name.value!!) == null &&
            !name.value!!.lowercase().equals("wszystkie") &&
            !name.value!!.trim().equals(""))
            return true
        return false
    }

    fun deleteCategory()
    {
        Dependencies.categoryDao.deleteCategory(category.categoryId)
    }

    class Factory(val editedId: Int) : ViewModelProvider.Factory
    {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EditCategoryViewModel(editedId) as T
        }
    }
}