package com.example.todonotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import android.widget.ViewSwitcher.ViewFactory
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.todonotes.databinding.FragmentAddCategoryBinding
import com.example.todonotes.viewModel.AddCategoryViewModel


class AddCategory : Fragment() {
    private lateinit var addCategoryViewModel: AddCategoryViewModel
    private lateinit var binding: FragmentAddCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addCategoryViewModel = ViewModelProvider(this, AddCategoryViewModel.Factory())[AddCategoryViewModel::class.java]

        binding = FragmentAddCategoryBinding.inflate(inflater, container, false)
        binding.addCategoryViewModel = addCategoryViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val saveButton = view.findViewById<Button>(R.id.saveButton)
        val cancelButton = view.findViewById<Button>(R.id.cancelButton)

        saveButton.setOnClickListener {
            if(addCategoryViewModel.canAdd()) {
                addCategoryViewModel.addCategory()
                Toast.makeText(requireContext(), "Dodano poprawnie kategorię!", Toast.LENGTH_SHORT)
                    .show()
                requireActivity().supportFragmentManager.popBackStack()
            }
            else
                Toast.makeText(requireContext(), "Kategoria o tej nazwie już istnieje", Toast.LENGTH_LONG).show()
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

        (requireActivity() as MainActivity).updateToolbarTitle("Dodaj kategorię")

        (requireActivity() as MainActivity).setBackArrowVisibility(true)
    }

}