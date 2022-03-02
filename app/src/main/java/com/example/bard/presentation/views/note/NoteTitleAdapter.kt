package com.example.bard.presentation.views.note

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bard.R
import com.example.bard.databinding.ItemNoteTitleBinding

class NoteTitleAdapter : RecyclerView.Adapter<NoteTitleAdapter.NoteTitleViewHolder>() {

    private var item = mutableListOf<String>()

    private var noteItemClickListener: NoteItemClickListener? = null

    interface NoteItemClickListener {
        fun titleClickListener(title: String)
        fun studyClickListener(title: String)
    }

    fun setNoteItemClickListener(listener: NoteItemClickListener) {
        noteItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteTitleViewHolder {
        return NoteTitleViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_note_title,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NoteTitleViewHolder, position: Int) {
        holder.bind(item[position], this)
    }

    override fun getItemCount() = item.size

    fun updateItem(item: MutableList<String>) {
        this.item = item
    }

    fun getAllItem() = item

    class NoteTitleViewHolder(private val binding: ItemNoteTitleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(title: String, noteTitleAdapter: NoteTitleAdapter) {
            binding.tvNoteTitle.apply {
                text = title
                setOnClickListener {
                    noteTitleAdapter.noteItemClickListener?.titleClickListener(title)
                }
            }

            binding.tvNoteStudy.apply {
                setOnClickListener {
                    noteTitleAdapter.noteItemClickListener?.studyClickListener(title)
                }
            }
        }
    }
}