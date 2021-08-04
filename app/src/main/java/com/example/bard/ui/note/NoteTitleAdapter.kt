package com.example.bard.ui.note

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bard.R
import com.example.bard.databinding.ItemNoteTitleBinding

class NoteTitleAdapter(
    private val item: List<String>
) : RecyclerView.Adapter<NoteTitleAdapter.NoteTitleViewHolder>() {

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
        holder.bind(item[position])
    }

    override fun getItemCount() = item.size

    class NoteTitleViewHolder(private val binding: ItemNoteTitleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(title: String) {
            binding.tvNoteTitle.text = title
        }
    }
}