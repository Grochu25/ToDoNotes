package com.example.todonotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.todonotes.repositories.Category

class MainCategoryAdapter(
    private val items: List<Category>,
    private val onCategoryClick: (Category) -> Unit = {}
) : RecyclerView.Adapter<MainCategoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryButton: Button = view.findViewById(R.id.categoryButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_main_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = items[position]
        holder.categoryButton.text = category.name

        holder.categoryButton.setOnClickListener {
            onCategoryClick(category)
        }
    }

    override fun getItemCount(): Int = items.size
}
