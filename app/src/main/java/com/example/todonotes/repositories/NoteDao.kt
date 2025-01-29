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
    @Query("SELECT * FROM note WHERE note.title == :title Order by note.priority DESC")
    fun getNoteByTitle(title: String): List<Note>

    @Transaction
    @Query("SELECT * FROM note, category WHERE note.categoryId == category.categoryId")
    fun getAllByCategories(): List<NoteCategory>

    @Transaction
    @Query("SELECT * FROM note, category WHERE note.categoryId == category.categoryId ORDER BY priority, isFavorite, title ASC")
    fun getAllOrderedByPrioFavTitle(): List<NoteCategory>

    @Transaction
    @Query("SELECT * FROM note, category WHERE note.categoryId == category.categoryId ORDER BY priority, isFavorite, date ASC")
    fun getAllOrderedByPrioFavDate(): List<NoteCategory>

    @Transaction
    @Query("SELECT category.name, COUNT(*) AS count FROM note, category WHERE note.categoryId == category.categoryId GROUP BY category.name")
    fun getNoteNumberByCategory(): List<NoteNumber>

    @Transaction
    @Query("SELECT * FROM note WHERE noteId = :notId")
    fun getNoteById(notId: Int): Note

    @Transaction
    @Query("SELECT * FROM note, category WHERE note.categoryId == category.categoryId AND note.categoryId = :catId")
    fun getNotesByCategoryId(catId: Int): List<NoteCategory>

    @Transaction
    @Query("SELECT * FROM note, category WHERE note.categoryId == category.categoryId AND (note.title LIKE ''%:search%'' OR note.desc LIKE ''%:search%'')")
    fun getAllSearched(search: String) : List<NoteCategory>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertAll(vararg notatki: Note)

    @Delete
    fun delete(notatka: Note)

    @Update
    fun update(notatka: Note)
}