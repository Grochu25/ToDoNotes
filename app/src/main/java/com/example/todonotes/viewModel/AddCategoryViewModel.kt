package com.example.todonotes.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todonotes.Dependencies
import com.example.todonotes.repositories.Category

class AddCategoryViewModel : ViewModel()
{
    var id:Int
    var categoryName = MutableLiveData<String>()

    init {
        var categoryList: List<Category> = Dependencies.categoryDao.getAll()
        id = if(categoryList.isEmpty()) 0 else (categoryList.sortedBy { it.categoryId }.last().categoryId+1)
        categoryName.value = ""
    }

    fun canAdd(): Boolean
    {
        return Dependencies.categoryDao.getCategoryByName(categoryName.value!!) == null
    }

    fun addCategory()
    {
        if(Dependencies.categoryDao.getCategoryById(id) == null
            && Dependencies.categoryDao.getCategoryByName(categoryName.value!!) == null){
            Dependencies.categoryDao.insertAll(
                Category(id, categoryName.value!!,0)
            )
        }
    }

    class Factory() : ViewModelProvider.Factory
    {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddCategoryViewModel() as T
        }
    }
}