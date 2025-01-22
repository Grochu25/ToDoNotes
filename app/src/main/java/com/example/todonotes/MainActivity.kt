package com.example.todonotes

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
        val highPriorityItems = listOf("Zamowic kontener" to "Remind 12:00 23.01", "Spotkanie" to "Remind 13:00 25.01")
        val mediumPriorityItems = listOf("Kupic wiadro" to "Remind 15:00 24.01")
        val lowPriorityItems = listOf("Spotkanie" to "Remind 15:00 24.01")

        val items = highPriorityItems + mediumPriorityItems + lowPriorityItems
        val priorities = List(highPriorityItems.size) { 1 } +
                List(mediumPriorityItems.size) { 2 } +
                List(lowPriorityItems.size) { 3 }

        val recyclerView = findViewById<RecyclerView>(R.id.high_priority_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PriorityAdapter(items, priorities)

        val highPrioritySection = findViewById<LinearLayout>(R.id.highPrioritySection)
        val mediumPrioritySection = findViewById<LinearLayout>(R.id.mediumPrioritySection)
        val lowPrioritySection = findViewById<LinearLayout>(R.id.lowPrioritySection)

        val highPriorityList = findViewById<RecyclerView>(R.id.high_priority_list)
        val mediumPriorityList = findViewById<RecyclerView>(R.id.medium_priority_list)
        val lowPriorityList = findViewById<RecyclerView>(R.id.low_priority_list)

        val visibleSections = mutableListOf<Pair<View, List<Pair<String, String>>>>()

        if (highPriorityItems.isNotEmpty()) {
            highPriorityList.layoutManager = LinearLayoutManager(this)
            highPriorityList.adapter = PriorityAdapter(
                highPriorityItems,
                List(highPriorityItems.size) { 1 }
            )
            highPrioritySection.visibility = View.VISIBLE
            visibleSections.add(highPrioritySection to highPriorityItems)
        }

        if (mediumPriorityItems.isNotEmpty()) {
            mediumPriorityList.layoutManager = LinearLayoutManager(this)
            mediumPriorityList.adapter = PriorityAdapter(
                mediumPriorityItems,
                List(highPriorityItems.size) { 2 }
                )
            mediumPrioritySection.visibility = View.VISIBLE
            visibleSections.add(mediumPrioritySection to mediumPriorityItems)
        }

        if (lowPriorityItems.isNotEmpty()) {
            lowPriorityList.layoutManager = LinearLayoutManager(this)
            lowPriorityList.adapter = PriorityAdapter(
                lowPriorityItems,
                List(highPriorityItems.size) { 3 }
            )
            lowPrioritySection.visibility = View.VISIBLE
            visibleSections.add(lowPrioritySection to lowPriorityItems)
        }

        adjustSectionWeights(visibleSections)
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
private fun adjustSectionWeights(sections: List<Pair<View, List<Pair<String, String>>>>) {
    val totalSections = sections.size
    val weight = 1f / totalSections

    for ((section, _) in sections) {
        val params = section.layoutParams as LinearLayout.LayoutParams
        params.weight = weight
        section.layoutParams = params
    }
}
