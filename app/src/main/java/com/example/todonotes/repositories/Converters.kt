package com.example.todonotes.repositories

import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.temporal.ChronoField
import java.util.Date

enum class Priority{
    NISKA, SREDNIA, WYSOKA
}

class LocalDateTimeConverter{
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromTimestamp(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): String? {
        return date?.toString()
    }
}

class ColorConverter{
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromInt(value: Int?): Color? {
        return value?.let { Color.valueOf(it) }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun colorToInt(color: Color?): Int? {
        return color?.toArgb()
    }
}

class PriorityConverter{
    @TypeConverter
    fun fromEnum(value: Priority?): Int?{
        if(value == Priority.WYSOKA) return 2
        else if(value == Priority.SREDNIA) return 1
        else return 0
    }
    @TypeConverter
    fun fromInt(value: Int?): Priority?{
        if(value == 2) return Priority.WYSOKA
        else if(value == 1) return Priority.SREDNIA
        else return Priority.NISKA
    }
}