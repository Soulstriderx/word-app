package com.fwrdgrp.wordapp.ui.home

import androidx.lifecycle.ViewModel
import com.fwrdgrp.wordapp.data.enums.SortBy
import com.fwrdgrp.wordapp.data.enums.SortOrder
import com.fwrdgrp.wordapp.data.models.Word
import com.fwrdgrp.wordapp.data.repo.WordsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseHomeViewModel(
    protected val repo: WordsRepo = WordsRepo.getInstance()
) : ViewModel() {
    protected val _words = MutableStateFlow<List<Word>>(emptyList())
    val words: StateFlow<List<Word>> = _words

    protected var currentSort = SortBy.DATE
    protected var currentOrder = SortOrder.ASCENDING
    protected var currentSearch = ""

    abstract fun getWords()

    fun refresh() {
        getWords()
    }

    fun setSearch(str: String) {
        currentSearch = str
        getWords()
    }

    fun setSorting(sortBy: SortBy, sortOrder: SortOrder) {
        currentSort = sortBy
        currentOrder = sortOrder
        getWords()
    }

    fun List<Word>.applySort(sortBy: SortBy, sortOrder: SortOrder): List<Word> {
        val sort = when (sortBy) {
            SortBy.TITLE -> {
                when (sortOrder) {
                    SortOrder.ASCENDING -> this.sortedBy { it.title.lowercase() }
                    SortOrder.DESCENDING -> this.sortedByDescending { it.title.lowercase() }
                }
            }

            SortBy.DATE -> {
                when (sortOrder) {
                    SortOrder.ASCENDING -> this.sortedBy { it.date }
                    SortOrder.DESCENDING -> this.sortedByDescending { it.date }
                }
            }
        }
        return sort
    }
}