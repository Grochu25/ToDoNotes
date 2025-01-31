package com.example.todonotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todonotes.viewModel.AllCategoriesViewModel

class CategoryNotes() : Fragment() {

    private lateinit var viewModel: AllCategoriesViewModel
    private lateinit var adapter: AllCategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category_notes, container, false)

        viewModel = ViewModelProvider(this, AllCategoriesViewModel.Factory())[AllCategoriesViewModel::class.java]

        adapter = AllCategoryAdapter(
            items = viewModel.categories.value!!,
            taskCounts = viewModel.categoryItemsCount.value!!,
            onDeleteClick = viewModel.delete
        )

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewCategories)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        return view
    }
}