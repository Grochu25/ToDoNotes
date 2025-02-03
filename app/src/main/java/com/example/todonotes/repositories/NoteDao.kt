package com.example.todonotes.repositories

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface NoteDao {
    @Transaction
    @Query("SELECT * FROM note")
    fun getAll(): List<Note>

    @Transaction
    @Query("SELECT * FROM note WHERE note.priority == :priority")
    fun getAllByPriority(priority: Priority): List<Note>

    @Transaction
    @Query("SELECT * FROM note WHERE note.title LIKE :title Order by note.priority DESC")
    fun getNoteByTitle(title: String): List<Note>

    @Transaction
    @Query("SELECT * FROM note, category WHERE note.categoryId == category.categoryId")
    fun getAllByCategories(): List<NoteCategory>

    @Transaction
    @Query("SELECT * FROM note, category WHERE note.categoryId == category.categoryId ORDER BY priority, isFavorite, title ASC")
    fun getAllOrderedByPrioFavTitle(): List<NoteCategory>

    @Transaction
    @Query("SELECT * FROM note WHERE note.priority == :priority ORDER BY isFavorite, date ASC")
    fun getAllWithPrioOrderedByFavDateASC(priority: Priority): List<Note>

    @Transaction
    @Query("SELECT * FROM note WHERE note.priority == :priority ORDER BY isFavorite, date DESC")
    fun getAllWithPrioOrderedByFavDateDESC(priority: Priority): List<Note>

    @Transaction
    @Query("SELECT * FROM note WHERE note.priority == :priority ORDER BY isFavorite, title ASC")
    fun getAllWithPrioOrderedByFavTitleASC(priority: Priority): List<Note>

    @Transaction
    @Query("SELECT * FROM note WHERE note.priority == :priority ORDER BY isFavorite, title DESC")
    fun getAllWithPrioOrderedByFavTitleDESC(priority: Priority): List<Note>

    @Query("SELECT * FROM note WHERE noteId = :notId")
    fun getNoteById(notId: Int): Note


    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertAll(vararg notatki: Note)

    @Delete
    fun delete(notatka: Note)

    @Update
    fun update(notatka: Note)
}