package com.example.todonotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog


class AddCategory : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_category, container, false)

        val saveButton = view.findViewById<Button>(R.id.saveButton)
        val cancelButton = view.findViewById<Button>(R.id.cancelButton)

        saveButton.setOnClickListener {
            Toast.makeText(requireContext(), "Dodano poprawnie kategorię!", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.popBackStack()
        }

        cancelButton.setOnClickListener{
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Potwierdzenie")
            builder.setMessage("Czy na pewno chcesz anulować dodawanie kategorii?")

            builder.setNegativeButton("Nie"){ dialog, _ ->
                dialog.dismiss()
            }

            builder.setPositiveButton("Tak"){ _, _ ->
                Toast.makeText(requireContext(), "Dodano poprawnie kategorię!", Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.popBackStack()
            }

            var dialog = builder.create()
            dialog.show()
        }

        return view
    }
}