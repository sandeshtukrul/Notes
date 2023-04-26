package com.example.notes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.model.Note

class NoteAdapter(private val listener: ClickListener) :
    ListAdapter<Note, NoteAdapter.NoteHolder>(DiffUtils()) {

    class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: LinearLayout = itemView.findViewById(R.id.card)
        private val title: TextView = itemView.findViewById(R.id.note_title)
        private val content: TextView = itemView.findViewById(R.id.note_content)

        fun bind(note: Note) {
            title.text = note.title
            content.text = note.description
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        return NoteHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentNote = getItem(position)

        holder.bind(currentNote)

        holder.card.setOnClickListener {
            currentNote?.let {
                listener.onItemClick(it.id)
            }
        }

        holder.card.setOnLongClickListener {
            currentNote?.let {
                listener.onLongItemClick(it.id)
            }
            return@setOnLongClickListener true
        }
    }
}

class DiffUtils : ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

}

interface ClickListener {
    fun onItemClick(id: Int)
    fun onLongItemClick(id: Int)
}