package com.example.todonotes

import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.todonotes.repositories.Note

class SearchNotesAdapter(private val items: List<Note>, private var onClickListener: (Note) -> Unit):
    RecyclerView.Adapter<PriorityAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val searchEditText: EditText = view.findViewById(R.id.search_edit_text)
    }

    override fun onBindViewHolder(holder: PriorityAdapter.ViewHolder, position: Int) {
        val item = items[position]
    }
}

//search_edit_text
