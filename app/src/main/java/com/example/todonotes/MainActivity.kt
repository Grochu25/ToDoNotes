package com.example.todonotes

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.todonotes.repositories.Category
import com.example.todonotes.repositories.NotesDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fragment_container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, NotesMain())
                .commit()
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

//        supportActionBar?.title = "BackArrowToolbar"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        var db = Room.databaseBuilder(
            applicationContext,
            NotesDatabase::class.java, "notatki-database"
        ).allowMainThreadQueries().build()

        Dependencies.noteDao = db.noteDao()
        Dependencies.categoryDao = db.categoryDao()

        if(db.categoryDao().getAll().isEmpty())
            Dependencies.categoryDao.insertAll(Category(0, "Szkoła", Color.RED))
        val kategorie: List<Category> = Dependencies.categoryDao.getAll()
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
    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return true
    }

    fun setBackArrowVisibility(visible: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(visible)
    }

    fun updateToolbarTitle(title: String) {
        supportActionBar?.title = title
    }
}