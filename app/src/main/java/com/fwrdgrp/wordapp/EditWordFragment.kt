package com.fwrdgrp.wordapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult

class EditWordFragment(
    val wordId: Int
) : BaseManageWordFragment() {

    private val viewModel: EditWordViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSubmit.text = "Update"

        val word = repo.getNoteById(wordId) ?: throw Exception("Word is null")

        binding.run {
            etTitle.setText(word.title)
            etMeaning.setText(word.meaning)
            etSynonym.setText(word.synonym)
            etDetails.setText(word.details)

            btnSubmit.setOnClickListener {
                repo.updateNote(
                    wordId,
                    word.copy(
                        title = etTitle.text.toString(),
                        desc = etMeaning.text.toString(),
                        synonym = etSynonym.text.toString(),
                        details = etDetails.text.toString()
                    )
                )
                setFragmentResult(
                    "manage_word",
                    Bundle().apply {
                        putBoolean("refresh", true)
                    }
                )
            }
        }
    }
}