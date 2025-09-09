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
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fwrdgrp.wordapp.ui.adapter.WordsAdapter
import com.fwrdgrp.wordapp.data.enums.SortBy
import com.fwrdgrp.wordapp.data.enums.SortOrder
import com.fwrdgrp.wordapp.data.util.Constant
import com.fwrdgrp.wordapp.databinding.FragmentHomeBinding
import com.fwrdgrp.wordapp.ui.manage.SortDialogFragment
import kotlinx.coroutines.launch

abstract class BaseHomeManageFragment : Fragment() {
    protected lateinit var binding: FragmentHomeBinding
    protected lateinit var adapter: WordsAdapter
    protected abstract val viewModel: BaseHomeViewModel

    //Used to send information to SortDialogFragment to pre-check radio buttons
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

        //To check and see if the view is empty, and display accordingly
        lifecycleScope.launch {
            viewModel.words.collect {
                adapter.setWords(it)
                binding.llEmpty.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            }
        }
        //To receive a bundle and refresh Home and CompleteWords
        setFragmentResultListener(Constant.MANAGE_WORD) { _, _ -> viewModel.refresh() }
    }

    fun setupAdapter() {
        adapter = WordsAdapter(
            emptyList(),
            onPress = {
                navigateToDetails(it.id!!)
            }
        )

        binding.rvWords.adapter = adapter
        binding.rvWords.layoutManager = LinearLayoutManager(this.context)
    }

    //To navigate to WordDetailFragment
    protected fun navigateToDetails(wordId: Int) {
        val action = getWordDetailAction(wordId)
        findNavController().navigate(action)
    }

    //To get the NavDirections to WordDetails dynamically, as it differs in CompleteWordFragment
    //and HomeFragment
    protected abstract fun getWordDetailAction(wordId: Int): NavDirections

    //Information received by the SortDialogFragment and is sent to the viewModel to sort.
    fun setSort(sortBy: SortBy, orderBy: SortOrder) {
        currentSort = sortBy
        currentOrder = orderBy
        viewModel.setSorting(sortBy, orderBy)
    }

    //This allows the EditText to detect text changes and filter accordingly
    fun setTextListener() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                viewModel.setSearch(p0.toString())
            }
            //I was forced to override these <--- I have no idea what the below does
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

    //This displays the SortDialogFragment.
    fun setNavigation() {
        binding.ivSort.setOnClickListener {
            val dialog = SortDialogFragment(currentSort, currentOrder) { sortBy, orderBy ->
                setSort(sortBy, orderBy)
            }
            dialog.show(parentFragmentManager, Constant.SORTING_DIALOG)
        }
    }
}