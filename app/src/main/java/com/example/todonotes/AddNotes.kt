package com.example.todonotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class AddNotes : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_notes, container, false)

        val cancelButton = view.findViewById<Button>(R.id.cancelButton)
        cancelButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        val saveButton = view.findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }

        return view
    }
}