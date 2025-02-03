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

class CategoryNotesAdapter(
    private val notes: List<Note>,
    private val onNoteClick: (Note) -> Unit,
    private val onStarClick: (Note) -> Unit
) : RecyclerView.Adapter<CategoryNotesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.titleText)
        val remindDateText: TextView = itemView.findViewById(R.id.remindDateText)
        val starButton: ImageButton = itemView.findViewById(R.id.starButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]

        val remindDate = note.date?.format(DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy"))

        holder.titleText.text = note.title ?: ""

        holder.remindDateText.text = remindDate

        holder.itemView.setOnClickListener {
            onNoteClick(note)
        }

        holder.starButton.setOnClickListener {
            onStarClick(note)
        }
    }

    override fun getItemCount(): Int = notes.size
}
