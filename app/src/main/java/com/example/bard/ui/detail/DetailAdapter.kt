package com.example.bard.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bard.R
import com.example.bard.databinding.ItemDetailWordBinding
import com.example.bard.domain.model.AddContent

class DetailAdapter(
    private val itemList: List<AddContent>
) : RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_detail_word,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount() = itemList.size

    fun getAllItem() = itemList

    class DetailViewHolder(private val binding: ItemDetailWordBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AddContent) {
            binding.apply {
                tvWord.text = item.word
                tvMeaning.text = item.meaning
            }
        }
    }
}