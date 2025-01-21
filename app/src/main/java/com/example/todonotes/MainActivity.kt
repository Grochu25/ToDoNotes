package com.example.todonotes

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.todonotes.repositories.Category
import com.example.todonotes.repositories.Note
import com.example.todonotes.repositories.NoteCategory
import com.example.todonotes.repositories.NotesDatabase
import com.example.todonotes.repositories.Priority
import java.util.Date

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        var db = Room.databaseBuilder(
//            applicationContext,
//            NotesDatabase::class.java, "notatki-database"
//        ).allowMainThreadQueries().build()
//
//        val kategoriaDao = db.categoryDao()
//        kategoriaDao.insertAll(Category(0, "Szkoła", Color.RED))
//        val kategorie: List<Category> = kategoriaDao.getAll()
//
//        Log.i("Database", kategorie.toString())
//
//        val notatkiDao = db.noteDao()
//        notatkiDao.insertAll(Note(0, "Zrób coś", "Coś", Date(2025, 1, 12), Priority.WYSOKA, 0, true))
//        val notatki: List<NoteCategory> = notatkiDao.getAll()
//        for(el in notatki){
//            for(el2 in el.notes){
//                Log.i("Database", el2.noteId.toString()+": "+el2.title+", "+el2.desc+", "+el2.date.toString()+", "+el2.priority+", "+el.category.name+": "+el.category.color.toString()+", "+el2.isFavorite)
//            }
//        }
//        Log.i("Database", notatki.toString())
    }
}