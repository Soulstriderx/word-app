package com.fwrdgrp.wordapp.ui.manage

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class AddWordFragment : BaseManageWordFragment() {
    private val viewModel: AddWordViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSubmit.text = "Add"

        binding.btnSubmit.setOnClickListener {
            viewModel.addWord(
                binding.etTitle.text.toString(),
                binding.etMeaning.text.toString(),
            )
        }

        lifecycleScope.launch {
            viewModel.finish.collect {
                submit()
            }
        }

        lifecycleScope.launch {
            viewModel.error.collect {
                showError(it)
            }
        }
    }
}