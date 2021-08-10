package com.example.bard.ui.note

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bard.R
import com.example.bard.databinding.ItemNoteTitleBinding

class NoteTitleAdapter : RecyclerView.Adapter<NoteTitleAdapter.NoteTitleViewHolder>() {

    private var item = mutableListOf<String>()

    private var titleClickListener: TitleClickListener? = null

    interface TitleClickListener {
        fun titleClickListener(title: String)
    }

    fun titleClickListener(listener: TitleClickListener) {
        titleClickListener = listener
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
                    noteTitleAdapter.titleClickListener?.titleClickListener(title)
                }
            }
        }
    }
}