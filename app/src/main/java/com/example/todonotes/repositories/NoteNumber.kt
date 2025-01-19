package com.example.todonotes.repositories

import androidx.room.ColumnInfo

data class NoteNumber(
    @ColumnInfo(name = "category") val category: String?,
    @ColumnInfo(name = "count") val count: Int?,
)