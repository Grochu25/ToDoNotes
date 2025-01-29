package com.example.todonotes.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface CategoryDao {
    @Transaction
    @Query("SELECT * FROM category")
    fun getAll(): List<Category>

    @Transaction
    @Query("SELECT * FROM category WHERE categoryId == :categoryId")
    fun getCategoryById(categoryId: Int): Category

    @Transaction
    @Query("SELECT * FROM category WHERE name == :name")
    fun getCategoryByName(name: String): Category

    @Insert
    fun insertAll(vararg kategorie: Category)
}