package com.example.todonotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.todonotes.databinding.FragmentEditCategoryBinding
import com.example.todonotes.viewModel.EditCategoryViewModel

private const val CATEGORY_ID = "cat_id"


class EditCategory : Fragment() {
    private var categoryId: Int = 0
    private lateinit var editCategoryViewModel: EditCategoryViewModel
    private lateinit var binding: FragmentEditCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoryId = it.getInt(CATEGORY_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        editCategoryViewModel = ViewModelProvider(this, EditCategoryViewModel.Factory(categoryId))[EditCategoryViewModel::class.java]
        binding = FragmentEditCategoryBinding.inflate(inflater, container, false)
        binding.editCategoryViewModel = editCategoryViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.cancelButton).setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }

        view.findViewById<Button>(R.id.deleteButton).setOnClickListener{
            editCategoryViewModel.deleteCategory()
            requireActivity().supportFragmentManager.popBackStack()
        }

        view.findViewById<Button>(R.id.saveButton).setOnClickListener{
            if(editCategoryViewModel.canSaveChanges()) {
                editCategoryViewModel.updateInDatabase()
                requireActivity().supportFragmentManager.popBackStack()
            }
            else{
                Toast.makeText(requireContext(), "Nazwa już istnieje lub jest pusta!", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(categoryId: Int) =
            EditCategory().apply {
                arguments = Bundle().apply {
                    putInt(CATEGORY_ID, categoryId)
                }
            }
    }
}