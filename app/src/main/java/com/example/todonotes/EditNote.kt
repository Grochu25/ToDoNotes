package com.example.todonotes

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.todonotes.databinding.FragmentEditNotesBinding
import com.example.todonotes.viewModel.EditViewModel

const val NOTE_ID = "noteId"

@RequiresApi(Build.VERSION_CODES.O)
class EditNote : Fragment() {
    private var noteId = 0
    private lateinit var editNoteViewMode: EditViewModel
    private lateinit var binding: FragmentEditNotesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            noteId = it.getInt(NOTE_ID)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        editNoteViewMode = ViewModelProvider(this, EditViewModel.Factory(noteId))[EditViewModel::class.java]

        binding = FragmentEditNotesBinding.inflate(inflater, container, false)
        binding.editViewModel = editNoteViewMode
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<Button>(R.id.cancelButton).setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        requireActivity().findViewById<Button>(R.id.saveButton).setOnClickListener {
            editNoteViewMode.saveChangesToDatabase()
            requireActivity().supportFragmentManager.popBackStack()
        }

        requireActivity().findViewById<Button>(R.id.deleteButton).setOnClickListener {
            editNoteViewMode.deleteNote()
            requireActivity().supportFragmentManager.popBackStack()
        }

        (requireActivity() as MainActivity).updateToolbarTitle("Edytuj notatkę")

        (requireActivity() as MainActivity).setBackArrowVisibility(true)
    }

    companion object{
        @JvmStatic
        fun newInstance(noteId: Int) =
            EditNote().apply {
                arguments = Bundle().apply {
                    putInt(NOTE_ID, noteId)
                }
            }
    }
}