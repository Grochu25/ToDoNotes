package com.example.todonotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NotesMain : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notes_main, container, false)

        val addNotesButton = view.findViewById<FloatingActionButton>(R.id.addNewNotes)
        val searchNotesButton = view.findViewById<ImageButton>(R.id.searchButton)
        val allCategoriesButton = view.findViewById<ImageButton>(R.id.allCategories)

        val highPriorityList = view.findViewById<RecyclerView>(R.id.highPriorityList)
        val mediumPriorityList = view.findViewById<RecyclerView>(R.id.mediumPriorityList)
        val lowPriorityList = view.findViewById<RecyclerView>(R.id.lowPriorityList)

        val highPriorityItems = listOf("Zamowic kontener" to "Remind 12:00 23.01",
                                        "Spotkanie" to "Remind 13:00 25.01"
                                        )
        val mediumPriorityItems = listOf("Kupic wiadro" to "Remind 15:00 24.01")
        val lowPriorityItems = listOf("Spotkanie" to "Remind 15:00 24.01",
                                        "Fryzjer" to "Remind 15:30 25.02"
            )

        highPriorityList.layoutManager = LinearLayoutManager(context)
        highPriorityList.adapter = PriorityAdapter(highPriorityItems, List(highPriorityItems.size) { 1 })

        mediumPriorityList.layoutManager = LinearLayoutManager(context)
        mediumPriorityList.adapter = PriorityAdapter(mediumPriorityItems, List(mediumPriorityItems.size) { 2 })

        lowPriorityList.layoutManager = LinearLayoutManager(context)
        lowPriorityList.adapter = PriorityAdapter(lowPriorityItems, List(lowPriorityItems.size) { 3 })

        val highPrioritySection = view.findViewById<LinearLayout>(R.id.highPrioritySection)
        val mediumPrioritySection = view.findViewById<LinearLayout>(R.id.mediumPrioritySection)
        val lowPrioritySection = view.findViewById<LinearLayout>(R.id.lowPrioritySection)

        val visibleSections = mutableListOf<Pair<View, List<Pair<String, String>>>>()

        if (highPriorityItems.isNotEmpty()) {
            highPrioritySection.visibility = View.VISIBLE
            visibleSections.add(highPrioritySection to highPriorityItems)
        }

        if (mediumPriorityItems.isNotEmpty()) {
            mediumPrioritySection.visibility = View.VISIBLE
            visibleSections.add(mediumPrioritySection to mediumPriorityItems)
        }

        if (lowPriorityItems.isNotEmpty()) {
            lowPrioritySection.visibility = View.VISIBLE
            visibleSections.add(lowPrioritySection to lowPriorityItems)
        }

        adjustSectionWeights(visibleSections)

        addNotesButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AddNotes())
                .addToBackStack(null)
                .commit()
        }

        searchNotesButton.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SearchNotes())
                .addToBackStack(null)
                .commit()
        }

        allCategoriesButton.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AllCategories())
                .addToBackStack(null)
                .commit()
        }

        return view
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
}
