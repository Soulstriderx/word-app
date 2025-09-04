package com.fwrdgrp.wordapp.ui.home

import com.fwrdgrp.wordapp.data.enums.Status
import kotlinx.coroutines.flow.update

class HomeViewModel : BaseHomeViewModel() {
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


}