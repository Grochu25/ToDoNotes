package com.example.todonotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class SearchNotes : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val cancelButton = view.findViewById<Button>(R.id.cancel_button)
        cancelButton.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }

        return view
    }
}