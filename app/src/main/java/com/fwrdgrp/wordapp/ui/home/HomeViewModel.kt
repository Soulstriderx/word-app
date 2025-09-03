package com.fwrdgrp.wordapp.ui.home

import com.fwrdgrp.wordapp.data.enums.SortBy
import com.fwrdgrp.wordapp.data.enums.SortOrder
import com.fwrdgrp.wordapp.data.enums.Status
import com.fwrdgrp.wordapp.data.models.Word
import kotlinx.coroutines.flow.update

class HomeViewModel : BaseHomeViewModel() {

    private var currentSort = SortBy.DATE
    private var currentOrder = SortOrder.ASCENDING
    private var currentSearch = ""
    init {
        getWords()
    }

    override fun getWords() {
        _words.update {
            repo.getWords().filter { it.status == Status.INCOMPLETE }
                .filter {
                    currentSearch.isBlank() || it.title.contains(currentSearch, ignoreCase = true)
                }.applySort(currentSort, currentOrder)
        }
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