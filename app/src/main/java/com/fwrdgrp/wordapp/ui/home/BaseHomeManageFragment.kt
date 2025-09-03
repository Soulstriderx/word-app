package com.fwrdgrp.wordapp.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fwrdgrp.wordapp.adapter.WordsAdapter
import com.fwrdgrp.wordapp.data.enums.SortBy
import com.fwrdgrp.wordapp.data.enums.SortOrder
import com.fwrdgrp.wordapp.databinding.FragmentHomeBinding
import com.fwrdgrp.wordapp.ui.manage.SortDialogFragment
import kotlinx.coroutines.launch

abstract class BaseHomeManageFragment : Fragment() {
    protected lateinit var binding: FragmentHomeBinding
    protected lateinit var adapter: WordsAdapter
    protected abstract val viewModel: BaseHomeViewModel
    protected var currentSort = SortBy.DATE
    protected var currentOrder = SortOrder.ASCENDING

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setTextListener()
        setNavigation()
        lifecycleScope.launch {
            viewModel.words.collect {
                adapter.setWords(it)
                binding.llEmpty.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            }
        }
        setFragmentResultListener("manage_word") { _, _ -> viewModel.refresh() }
    }

    fun setupAdapter() {
        adapter = WordsAdapter(
            emptyList(),
            onPress = {
                val action = HomeFragmentDirections.actionHomeToWordDetail(it.id!!)
                findNavController().navigate(action)
            }
        )

        binding.rvWords.adapter = adapter
        binding.rvWords.layoutManager = LinearLayoutManager(this.context)
    }

    fun setSort(sortBy: SortBy, orderBy: SortOrder) {
        currentSort = sortBy
        currentOrder = orderBy
        viewModel.setSorting(sortBy, orderBy)
    }

    fun setTextListener() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                viewModel.setSearch(p0.toString())
            }
            //I was forced to override these
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

    fun setNavigation() {
        binding.fabAdd.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToAddWord()
            findNavController().navigate(action)
        }
        binding.ivSort.setOnClickListener {
            val dialog = SortDialogFragment(currentSort, currentOrder) { sortBy, orderBy ->
                setSort(sortBy, orderBy)
            }
            dialog.show(parentFragmentManager, "SortingDialog")
        }
    }
}