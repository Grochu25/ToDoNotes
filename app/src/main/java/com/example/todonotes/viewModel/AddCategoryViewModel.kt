package com.example.todonotes.viewModel

import android.appwidget.AppWidgetManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todonotes.Dependencies
import com.example.todonotes.repositories.Category

class AddCategoryViewModel : ViewModel()
{
    var id: Int
    var categoryName = MutableLiveData<String>()
    var categoryColor = MutableLiveData<Int>()


    init {
        var categoryList: List<Category> = Dependencies.categoryDao.getAll()
        id = if(categoryList.isEmpty()) 0 else (categoryList.sortedBy { it.categoryId }.last().categoryId+1)
        categoryName.value = ""
        categoryColor.value = 0xFF800080.toInt()
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
                Category(id, categoryName.value!!, categoryColor.value)
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