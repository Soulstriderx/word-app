package com.fwrdgrp.wordapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fwrdgrp.wordapp.data.models.Word

class WordsAdapter(
    private var words: List<Word>,
    private val onPress: (Word) -> Unit
): RecyclerView.Adapter<WordsAdapter.WordViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsAdapter.WordViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutWordBinding.inflate(inflater,parent, false)
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordsAdapter.WordViewHolder, position: Int) {
        val word = words[position]
        holder.binding.tvTitle.text = word.title
        holder.binding.tvMeaning.text = word.meaning
        holder.binding.tvSynonym.text = word.synonym
        holder.binding.tvDetails.text = word.details
//        holder.binding.tvStatus.text = word.status

        holder.binding.run {
            tvTitle.text = word.title
            tvMeaning.text = word.meaning
            tvSynonym.text = word.synonym
            tvDetails.text = word.details
//            tvStatus.text = word.status

            cvWord.setOnClickListener {
                onPress(word)
            }
        }
    }

    override fun getItemCount() = words.size

    fun setWords(words: List<Word>) {
        this.words = words
        notifyDataSetChanged()
    }


    class WordViewHolder(
        val binding: ItemLayoutWordBinding
    ): RecyclerView.ViewHolder(binding.root)
    }