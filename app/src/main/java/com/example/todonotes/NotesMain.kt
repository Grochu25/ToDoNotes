package com.example.todonotes

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todonotes.repositories.Note
import com.example.todonotes.viewModel.NotesMainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

@RequiresApi(Build.VERSION_CODES.O)
class NotesMain : Fragment() {
    private lateinit var notesMainViewModel: NotesMainViewModel

    private var isSortedByDate = true // default we sort by date

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notes_main, container, false)
        notesMainViewModel = ViewModelProvider(this, NotesMainViewModel.Factory())[NotesMainViewModel::class.java]

        val addNotesButton = view.findViewById<FloatingActionButton>(R.id.addNewNotes)
        val searchNotesButton = view.findViewById<ImageButton>(R.id.searchButton)
        val allCategoriesButton = view.findViewById<ImageButton>(R.id.allCategories)
        val sortedByButton = view.findViewById<ImageButton>(R.id.sortedBy)

        val highPriorityList = view.findViewById<RecyclerView>(R.id.highPriorityList)
        val mediumPriorityList = view.findViewById<RecyclerView>(R.id.mediumPriorityList)
        val lowPriorityList = view.findViewById<RecyclerView>(R.id.lowPriorityList)

        highPriorityList.layoutManager = LinearLayoutManager(context)
        highPriorityList.adapter = PriorityAdapter(notesMainViewModel.highPriorityItems.value!!, List(notesMainViewModel.highPriorityItems.value!!.size) { 1 }, noteDetails)

        mediumPriorityList.layoutManager = LinearLayoutManager(context)
        mediumPriorityList.adapter = PriorityAdapter(notesMainViewModel.mediumPriorityItems.value!!, List(notesMainViewModel.mediumPriorityItems.value!!.size) { 2 }, noteDetails)

        lowPriorityList.layoutManager = LinearLayoutManager(context)
        lowPriorityList.adapter = PriorityAdapter(notesMainViewModel.lowPriorityItems.value!!, List(notesMainViewModel.lowPriorityItems.value!!.size) { 3 }, noteDetails)

        sectionsVisibility()

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

        sortedByButton.setOnClickListener{showSortMenu(it)}

        return view
    }

    private fun sectionsVisibility()
    {
        val highPrioritySection = view?.findViewById<LinearLayout>(R.id.highPrioritySection)
        val mediumPrioritySection = view?.findViewById<LinearLayout>(R.id.mediumPrioritySection)
        val lowPrioritySection = view?.findViewById<LinearLayout>(R.id.lowPrioritySection)

        val visibleSections = mutableListOf<Pair<View, List<Pair<String, String>>>>()

        if (notesMainViewModel.highPriorityItems.value!!.isNotEmpty()) {
            highPrioritySection?.visibility = View.VISIBLE
        }

        if (notesMainViewModel.mediumPriorityItems.value!!.isNotEmpty()) {
            mediumPrioritySection?.visibility = View.VISIBLE
        }

        if (notesMainViewModel.lowPriorityItems.value!!.isNotEmpty()) {
            lowPrioritySection?.visibility = View.VISIBLE
        }

        adjustSectionWeights(visibleSections)
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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).updateToolbarTitle("Twoje notatki")

        (requireActivity() as MainActivity).setBackArrowVisibility(false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()


        resetAdapters()

        sectionsVisibility()
    }

    fun resetAdapters(){
        notesMainViewModel.reloadDataFromDB()

        val highPriorityList = requireActivity().findViewById<RecyclerView>(R.id.highPriorityList)
        val mediumPriorityList = requireActivity().findViewById<RecyclerView>(R.id.mediumPriorityList)
        val lowPriorityList = requireActivity().findViewById<RecyclerView>(R.id.lowPriorityList)

        highPriorityList.adapter = PriorityAdapter(notesMainViewModel.highPriorityItems.value!!, List(notesMainViewModel.highPriorityItems.value!!.size) { 1 }, noteDetails)
        mediumPriorityList.adapter = PriorityAdapter(notesMainViewModel.mediumPriorityItems.value!!, List(notesMainViewModel.mediumPriorityItems.value!!.size) { 2 }, noteDetails)
        lowPriorityList.adapter = PriorityAdapter(notesMainViewModel.lowPriorityItems.value!!, List(notesMainViewModel.lowPriorityItems.value!!.size) { 3 }, noteDetails)
    }

    val noteDetails: (Note) -> Unit =
    {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, EditNote.newInstance(it.noteId))
            .addToBackStack(null)
            .commit()
    }

    private fun showSortMenu(view: View) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.menuInflater.inflate(R.menu.sort_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.sort_by_date_asc -> {
                    notesMainViewModel.sortType = NotesMainViewModel.SortType.DATE_ASC
                    resetAdapters()
                    true
                }
                R.id.sort_by_date_desc -> {
                    isSortedByDate = false
                    notesMainViewModel.sortType = NotesMainViewModel.SortType.DATE_DESC
                    resetAdapters()
                    true
                }
                R.id.sort_by_name_asc -> {
                    notesMainViewModel.sortType = NotesMainViewModel.SortType.NAME_ASC
                    resetAdapters()
                    true
                }
                R.id.sort_by_name_desc -> {
                    notesMainViewModel.sortType = NotesMainViewModel.SortType.NAME_DESC
                    resetAdapters()
                    true
                }
                else -> false
            }

        }
        popupMenu.show()
    }

}
