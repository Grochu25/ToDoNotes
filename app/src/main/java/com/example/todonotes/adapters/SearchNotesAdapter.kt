package com.example.todonotes.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.todonotes.R
import com.example.todonotes.repositories.Note
import java.time.format.DateTimeFormatter

class SearchNotesAdapter(
    private val items: MutableList<Note>,
    private val onClickListener: (Note) -> Unit
) : RecyclerView.Adapter<SearchNotesAdapter.ViewHolder>() {

    private var filteredList: List<Note> = items

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
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
        val note = filteredList[position]

        holder.remindText.text = note.title
        holder.remindDateText.text = "Przypomnienie: " + note.date?.format(DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy"))

        holder.itemView.setOnClickListener{
            onClickListener(note)
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
    }

    override fun getItemCount(): Int = filteredList.size
}

