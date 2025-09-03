package com.fwrdgrp.wordapp.ui.manage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.fwrdgrp.wordapp.R
import com.fwrdgrp.wordapp.databinding.ManageItemLayoutBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

abstract class BaseManageFragment : Fragment() {
    protected lateinit var binding: ManageItemLayoutBinding
    protected abstract val viewModel: BaseManageViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ManageItemLayoutBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.finish.collect {
                navigateBack()
            }
        }
        lifecycleScope.launch {
            viewModel.error.collect {
                showError(it)
            }
        }
        binding.mtManage.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    fun showError(msg: String) {
        val snackbar = Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG)
        snackbar.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.red)).show()
    }

    fun navigateBack() {
        setFragmentResult("manage_word", Bundle())
        setFragmentResult("manage_edit_word", Bundle())
        findNavController().popBackStack()
    }
}