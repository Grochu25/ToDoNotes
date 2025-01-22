package com.example.todonotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PriorityAdapter(private val items: List<Pair<String, String>>, private val priorities: List<Int>) :
    RecyclerView.Adapter<PriorityAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val remindText: TextView = view.findViewById(R.id.titleText)
        val remindDateText: TextView = view.findViewById(R.id.remindDateText)
        val starButton: ImageButton = view.findViewById(R.id.starButton)
        val container: View = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.priority_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (title, remindDate) = items[position]
        holder.remindText.text = title
        holder.remindDateText.text = remindDate

        holder.starButton.setOnClickListener {
            val currentColor = holder.starButton.tag as? Boolean ?: false
            if (!currentColor) {
                holder.starButton.setImageResource(R.drawable.full_star)
                holder.starButton.tag = true
            } else {
                holder.starButton.setImageResource(R.drawable.outline_star)
                holder.starButton.tag = false
            }
        }

        when (priorities[position]) {
            1 -> holder.container.setBackgroundResource(R.drawable.border_high_item)
            2 -> holder.container.setBackgroundResource(R.drawable.border_medium_item)
            3 -> holder.container.setBackgroundResource(R.drawable.border_low_item)
        }
    }

    override fun getItemCount() = items.size
}
