package com.fwrdgrp.wordapp.ui.home

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.fwrdgrp.wordapp.MyApp
import com.fwrdgrp.wordapp.data.enums.Status
import com.fwrdgrp.wordapp.data.repo.WordsRepo
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    repo: WordsRepo
) : BaseHomeViewModel(repo) {
    init {
        getWords()
    }

    override fun getWords() {
        viewModelScope.launch {
            //Home filters if the word has been "Undone"
            repo.getAllWords().map { words -> words.filter { it.status == Status.INCOMPLETE }
                //This checks for a boolean, if currentSearch is blank, then it does nothing
                //If it is not blank, it will filter according to the title, ignoring case
                .filter { currentSearch.isBlank() || it.title.contains(currentSearch, ignoreCase = true) }
                //Below is the custom method.
                .applySort(currentSort, currentOrder)
                }
                .collect { filteredWords -> _words.update { filteredWords } }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository = (this[APPLICATION_KEY] as MyApp).repo
                HomeViewModel(repo = myRepository)
            }
        }
    }
}