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
    private lateinit var viewModel: NotesMainViewModel
    private lateinit var highPriorityAdapter: PriorityAdapter
    private lateinit var mediumPriorityAdapter: PriorityAdapter
    private lateinit var lowPriorityAdapter: PriorityAdapter

    private var isSortedByDate = true // default we sort by date

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notes_main, container, false)
        viewModel = ViewModelProvider(this, NotesMainViewModel.Factory())[NotesMainViewModel::class.java]

        val addNotesButton = view.findViewById<FloatingActionButton>(R.id.addNewNotes)
        val searchNotesButton = view.findViewById<ImageButton>(R.id.searchButton)
        val allCategoriesButton = view.findViewById<ImageButton>(R.id.allCategories)
        val sortedByButton = view.findViewById<ImageButton>(R.id.sortedBy)

        val highPriorityList = view.findViewById<RecyclerView>(R.id.highPriorityList)
        val mediumPriorityList = view.findViewById<RecyclerView>(R.id.mediumPriorityList)
        val lowPriorityList = view.findViewById<RecyclerView>(R.id.lowPriorityList)


        highPriorityList.layoutManager = LinearLayoutManager(context)
        highPriorityList.adapter = PriorityAdapter(viewModel.highPriorityItems.value!!, List(viewModel.highPriorityItems.value!!.size) { 1 }, noteDetails)

        mediumPriorityList.layoutManager = LinearLayoutManager(context)
        mediumPriorityList.adapter = PriorityAdapter(viewModel.mediumPriorityItems.value!!, List(viewModel.mediumPriorityItems.value!!.size) { 2 }, noteDetails)

        lowPriorityList.layoutManager = LinearLayoutManager(context)
        lowPriorityList.adapter = PriorityAdapter(viewModel.lowPriorityItems.value!!, List(viewModel.lowPriorityItems.value!!.size) { 3 }, noteDetails)

        val highPrioritySection = view.findViewById<LinearLayout>(R.id.highPrioritySection)
        val mediumPrioritySection = view.findViewById<LinearLayout>(R.id.mediumPrioritySection)
        val lowPrioritySection = view.findViewById<LinearLayout>(R.id.lowPrioritySection)

        val visibleSections = mutableListOf<Pair<View, List<Pair<String, String>>>>()

        if (viewModel.highPriorityItems.value!!.isNotEmpty()) {
            highPrioritySection.visibility = View.VISIBLE
        }

        if (viewModel.highPriorityItems.value!!.isNotEmpty()) {
            mediumPrioritySection.visibility = View.VISIBLE
        }

        if (viewModel.highPriorityItems.value!!.isNotEmpty()) {
            lowPrioritySection.visibility = View.VISIBLE
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

        sortedByButton.setOnClickListener{showSortMenu(it)}

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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).updateToolbarTitle("Twoje notatki")

        (requireActivity() as MainActivity).setBackArrowVisibility(false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()

        viewModel.reloadDataFromDB()

        val highPriorityList = requireActivity().findViewById<RecyclerView>(R.id.highPriorityList)
        val mediumPriorityList = requireActivity().findViewById<RecyclerView>(R.id.mediumPriorityList)
        val lowPriorityList = requireActivity().findViewById<RecyclerView>(R.id.lowPriorityList)

        highPriorityList.adapter = PriorityAdapter(viewModel.highPriorityItems.value!!, List(viewModel.highPriorityItems.value!!.size) { 1 }, noteDetails)
        mediumPriorityList.adapter = PriorityAdapter(viewModel.mediumPriorityItems.value!!, List(viewModel.mediumPriorityItems.value!!.size) { 2 }, noteDetails)
        lowPriorityList.adapter = PriorityAdapter(viewModel.lowPriorityItems.value!!, List(viewModel.lowPriorityItems.value!!.size) { 3 }, noteDetails)

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
                    isSortedByDate = true
                    // there will be connect with mvvm
                    true
                }
                R.id.sort_by_date_desc -> {
                    isSortedByDate = false
                    // there will be connect with mvvm
                    true
                }
                R.id.sort_by_name_asc -> {
                    // there will be connect with mvvm
                    true
                }
                R.id.sort_by_name_desc -> {
                    // there will be connect with mvvm
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

}
