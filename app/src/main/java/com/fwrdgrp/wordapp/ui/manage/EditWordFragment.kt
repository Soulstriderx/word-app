package com.fwrdgrp.wordapp.ui.manage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.fwrdgrp.wordapp.R
import com.fwrdgrp.wordapp.data.models.Word
import com.fwrdgrp.wordapp.ui.general.WordDetailFragmentArgs
import kotlin.getValue

class EditWordFragment: BaseManageFragment() {

    override val viewModel: EditWordViewModel by viewModels()
    private val args: WordDetailFragmentArgs by navArgs()
    private lateinit var word: Word

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        word = viewModel.getWord(args.wordId)
        binding.run {
            setText(word)
            mbSubmit.setOnClickListener {
                viewModel.submit(
                    word.copy(
                        title = etTitle.text.toString(),
                        meaning = etMeaning.text.toString(),
                        synonym = etSynonyms.text.toString(),
                        details = etDetails.text.toString()
                    )
                )
            }
        }
    }

    fun setText(word: Word?) {
        binding.run {
            mbSubmit.text = getString(R.string.update)
            mtManage.title = getString(R.string.update_word)
            etTitle.setText(word?.title)
            etMeaning.setText(word?.meaning)
            etSynonyms.setText(word?.synonym)
            etDetails.setText(word?.details)
        }
    }
}