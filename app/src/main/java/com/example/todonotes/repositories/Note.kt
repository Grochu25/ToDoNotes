package com.example.todonotes.repositories

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = arrayOf("categoryId"),
            childColumns = arrayOf("categoryId"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Note(
    @PrimaryKey val noteId: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "desc") val desc: String?,
    @ColumnInfo(name = "date") val date: LocalDateTime?,
    @ColumnInfo(name = "priority") val priority: Priority,
    @ColumnInfo(name = "categoryId") val categoryId: Int,
    @ColumnInfo(name = "isFavorite") val isFavorite: Boolean,
)