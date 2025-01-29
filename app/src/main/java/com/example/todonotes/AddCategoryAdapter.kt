package com.example.todonotes

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todonotes.repositories.Category

class AddCategoryAdapter(
    private val items: MutableList<Category>,
    private val taskCounts: Map<String, Int>,
    private val onClickListener: (Category) -> Unit,
    private val onDeleteClick: (Category) -> Unit
) : RecyclerView.Adapter<AddCategoryAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val category: TextView = view.findViewById(R.id.textCategoryName)
        val taskCounter: TextView = view.findViewById(R.id.textTaskCount)
        val deleteButton: ImageButton = view.findViewById(R.id.deleteButton)
        val editButton: ImageButton = view.findViewById(R.id.editButton)
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

        holder.itemView.setOnClickListener{
            onClickListener(items[position])
        }

        // temporarily skip the border color

//        holder.deleteButton.setOnClickListener{
//
//        }

//        holder.editButton.setOnClickListener{
//                 ->    AddCategory
//        }

    }

    override fun getItemCount(): Int = items.size
}

