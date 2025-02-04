package com.example.todonotes.adapters

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todonotes.R
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
        val drawable = ContextCompat.getDrawable(holder.itemView.context, R.drawable.rounded_button)

        if (category.categoryId == -1) {
            if (drawable is GradientDrawable) {
                drawable.setColor(Color.parseColor("#D3D3D3"))
                holder.categoryButton.background = drawable
            }
            holder.categoryButton.setTextColor(Color.BLACK)
        } else {
            if (drawable is GradientDrawable) {
                drawable.setColor(category.color ?: Color.GRAY)
                holder.categoryButton.background = drawable
            }
            holder.categoryButton.setTextColor(Color.WHITE)
        }

        //TODO tutaj jakoś wyszarzyć pozostałe kategorie
//        holder.categoryButton.setOnClickListener {
//            onCategoryClick(category)
//            items.forEach {
//                if(it.categoryId != items[position].categoryId)
//                    d
//            }
//        }
    }

    override fun getItemCount(): Int = items.size
}
