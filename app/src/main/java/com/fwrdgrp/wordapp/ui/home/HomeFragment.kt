package com.fwrdgrp.wordapp.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.fwrdgrp.wordapp.R

class HomeFragment : BaseHomeManageFragment() {
    override val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabAdd.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToAddWord()
            findNavController().navigate(action)
        }
        binding.tvEmpty.text = getString(R.string.home_empty)
    }

    override fun getWordDetailAction(wordId: Int): NavDirections {
        return HomeFragmentDirections.actionHomeToWordDetail(wordId)
    }
}
