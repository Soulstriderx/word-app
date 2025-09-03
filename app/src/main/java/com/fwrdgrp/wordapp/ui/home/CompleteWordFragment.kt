package com.fwrdgrp.wordapp.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.fwrdgrp.wordapp.R

class CompleteWordFragment : BaseHomeManageFragment() {
    override val viewModel: CompleteWordViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabAdd.setOnClickListener {
            val action = CompleteWordFragmentDirections.actionCompleteWordToAddWord()
            findNavController().navigate(action)
        }
        binding.tvEmpty.text = getString(R.string.complete_empty)
    }

    override fun getWordDetailAction(wordId: Int): NavDirections {
        return CompleteWordFragmentDirections.actionCompleteWordToWordDetail(wordId)
    }
}