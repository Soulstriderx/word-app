package com.fwrdgrp.wordapp.ui.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fwrdgrp.wordapp.adapter.WordsAdapter
import com.fwrdgrp.wordapp.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: WordsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()

        lifecycleScope.launch {
            viewModel.words.collect {
                adapter.setWords(it)
                binding.llEmpty.visibility = if(it.isEmpty()) View.VISIBLE else View.GONE
            }
        }
        binding.run {
            fabAdd.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeToAddWord()
                findNavController().navigate(action)
            }
            setFragmentResultListener("manage_word") { _, _ -> viewModel.refresh() }
            ivSort.setOnClickListener {
                val dialog = SortDialogFragment(currentSort, currentOrder) { sortBy, orderBy ->
                    setSort(sortBy, orderBy)
                }
                dialog.show(parentFragmentManager, "SortingDialog")
            }
            etSearch.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    viewModel.setSearch(p0.toString())
                }
                //I was forced to override these
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            })
        }
    }

    fun setupAdapter() {
        adapter = WordsAdapter(
            emptyList(),
            onPress = { val action = HomeFragmentDirections.actionHomeToWordDetail(it.id!!)
                findNavController().navigate(action) }
        )
        binding.rvWords.adapter = adapter
        binding.rvWords.layoutManager = LinearLayoutManager(this.context)
    }

    fun setSort(sortBy: SortBy, orderBy: SortOrder) {
        currentSort = sortBy
        currentOrder = orderBy
        viewModel.setSorting(sortBy, orderBy)
    }
}
