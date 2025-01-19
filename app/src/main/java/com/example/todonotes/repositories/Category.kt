package com.example.todonotes.repositories

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    @PrimaryKey val categoryId: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "color") val color: Int?,
)