package com.example.todonotes.repositories

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class NoteCategory(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "categoryId"
    )
    val notes: List<Note>
)