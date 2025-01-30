package com.example.todonotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.todonotes.viewModel.AllCategoriesViewModel

class AllCategories : Fragment() {
    private lateinit var allCategoryViewModel: AllCategoriesViewModel
    private lateinit var adapter: AllCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all_categories, container, false)
        allCategoryViewModel = ViewModelProvider(this, AllCategoriesViewModel.Factory())[AllCategoriesViewModel::class.java]
        adapter = AllCategoryAdapter(allCategoryViewModel.categories.value!!,
            allCategoryViewModel.categoryItemsCount.value!!,
            allCategoryViewModel.delete)

        val addCategory = view.findViewById<Button>(R.id.buttonAddCategory)

        addCategory.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AddCategory())
                .addToBackStack(null)
                .commit()        }

        val list = view.findViewById<RecyclerView>(R.id.recyclerViewCategories)
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).updateToolbarTitle("Wszystkie kategorie")

        (requireActivity() as MainActivity).setBackArrowVisibility(true)
    }

    override fun onResume() {
        super.onResume()
        allCategoryViewModel.reloadDataFromDB()
        adapter.notifyDataSetChanged()
    }
}