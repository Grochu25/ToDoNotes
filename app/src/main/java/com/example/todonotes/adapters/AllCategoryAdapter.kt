package com.example.todonotes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todonotes.R
import com.example.todonotes.repositories.Category

class AllCategoryAdapter(
    private val items: MutableList<Category>,
    private val taskCounts: Map<String, Int>,
    //private val onClickListener: (Category) -> Unit,
    private val onDeleteClick: (Category) -> Unit,
    private val onEditClick: (Category) -> Unit
) : RecyclerView.Adapter<AllCategoryAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val category: TextView = view.findViewById(R.id.textCategoryName)
        val taskCounter: TextView = view.findViewById(R.id.textTaskCount)
        val deleteButton: ImageButton = view.findViewById(R.id.deleteButton)
        val editButton: ImageButton = view.findViewById(R.id.editButton)
        val categoryButton = view.findViewById<Button>(R.id.categoryButton)
        val container: View = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = items[position]

        holder.category.text = category.name
        holder.taskCounter.text = taskCounts[category.name]?.toString() ?: "0"



        if (category.categoryId == -1) {
            holder.deleteButton.visibility = View.GONE
            holder.editButton.visibility = View.GONE
        } else {
            holder.deleteButton.visibility = View.VISIBLE
            holder.editButton.visibility = View.VISIBLE

            holder.itemView.setOnClickListener {
                onEditClick(items[position])
                notifyDataSetChanged()
            }
            holder.deleteButton.setOnClickListener {
                onDeleteClick(items[position])
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int = items.size
}

