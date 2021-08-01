package com.example.bard.ui.add

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bard.R
import com.example.bard.data.AddContent
import com.example.bard.databinding.ItemAddBinding

class AddItemAdapter(
    private val itemList: MutableList<AddContent>
) : RecyclerView.Adapter<AddItemAdapter.AddItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        return AddItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_add,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount() = itemList.size

    fun addItem() {
        itemList.add(AddContent.default())
        notifyDataSetChanged()
    }

    class AddItemViewHolder(binding: ItemAddBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AddContent) {

        }
    }
}