package com.example.bard.ui.add

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bard.R
import com.example.bard.data.AddContent
import com.example.bard.data.NoteData
import com.example.bard.databinding.ItemAddBinding

class AddItemAdapter(
    private val noteItem: NoteData,
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
        holder.bind(noteItem.wordList, position)
    }

    override fun getItemCount() = noteItem.wordList.size

    fun addItem() {
        noteItem.wordList.add(AddContent())
        notifyItemInserted(noteItem.wordList.size - 1)
    }

    /**
     * getAllItem
     * @desc 단어, 뜻 둘중 하나라도 있으면 추가
     */
    fun getAllItem() = noteItem

    class AddItemViewHolder(private val binding: ItemAddBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MutableList<AddContent>, position: Int) {
            binding.apply {
                val words = item[position]
                if (words.word.isNotEmpty()) { tvItemWord.setText(words.word) }
                if (words.meaning.isNotEmpty()) { tvItemMeaning.setText(words.meaning) }

                tvItemMeaning.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: Editable?) {
                        item[position] = setList(tvItemMeaning.text.toString().trim(), tvItemWord.text.toString().trim())
                    }
                })

                tvItemWord.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: Editable?) {
                        item[position] = setList(tvItemMeaning.text.toString().trim(), tvItemWord.text.toString().trim())
                    }
                })
            }
        }

        fun setList(meaning: String, wor: String) = AddContent(wor, meaning)
    }
}