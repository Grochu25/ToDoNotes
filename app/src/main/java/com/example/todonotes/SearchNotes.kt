package com.example.todonotes

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todonotes.adapters.SearchNotesAdapter
import com.example.todonotes.databinding.FragmentSearchBinding
import com.example.todonotes.repositories.Note
import com.example.todonotes.viewModel.SearchNotesViewModel

@RequiresApi(Build.VERSION_CODES.O)
class SearchNotes : Fragment() {
    private lateinit var searchNotesViewModel: SearchNotesViewModel
    private lateinit var binding: FragmentSearchBinding

    private lateinit var adapter: SearchNotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        searchNotesViewModel = ViewModelProvider(this, SearchNotesViewModel.Factory())[SearchNotesViewModel::class.java]

        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.searchNotesViewModel = searchNotesViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    val noteDetails: (Note) -> Unit =
        {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, EditNote.newInstance(it.noteId))
                .addToBackStack(null)
                .commit()
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SearchNotesAdapter(searchNotesViewModel.foundNotesList.value!!,noteDetails)

        val cancelButton = view.findViewById<Button>(R.id.cancel_button)
        cancelButton.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }

        val searchBar = view.findViewById<EditText>(R.id.search_edit_text)
        searchBar.addTextChangedListener(watcher())

        val NotesList = view.findViewById<RecyclerView>(R.id.recycler_view)
        NotesList.layoutManager = LinearLayoutManager(context)
        NotesList.adapter = adapter


        (requireActivity() as MainActivity).updateToolbarTitle("Wyszukaj")

        (requireActivity() as MainActivity).setBackArrowVisibility(true)
    }

    inner class watcher() : TextWatcher{
        override fun afterTextChanged(s : Editable) {}

        override fun beforeTextChanged(s : CharSequence, start : Int,
                                       count : Int, after : Int) {
        }

        override fun onTextChanged(s : CharSequence, start : Int,
                                   before : Int, count : Int) {
            adapter.notifyDataSetChanged()
        }
    }
}