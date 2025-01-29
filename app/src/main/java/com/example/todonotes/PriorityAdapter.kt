package com.example.todonotes

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.todonotes.repositories.Note
import java.time.format.DateTimeFormatter

class PriorityAdapter(private val items: List<Note>, private val priorities: List<Int>, private var onClickListener: (Note) -> Unit) :
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val title = items[position].title
        val remindDate = "Przypomnienie: "+items[position].date?.format(DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy"))
        holder.remindText.text = title
        holder.remindDateText.text = remindDate

        holder.itemView.setOnClickListener{
            onClickListener(items[position])
        }

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
