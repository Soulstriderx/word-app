package com.fwrdgrp.wordapp.ui.manage

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.fwrdgrp.wordapp.MyApp
import com.fwrdgrp.wordapp.data.models.Word
import com.fwrdgrp.wordapp.data.repo.WordsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddWordViewModel(
    repo: WordsRepo
): BaseManageViewModel(repo) {

    //Submits to repo.
    override fun submit(word: Word) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
            require(word.title.isNotBlank()) { "NO_TITLE" }
            require(word.meaning.isNotBlank()) { "NO_MEANING" }
            repo.addWord(word)
                _finish.emit(Unit)
            }
        } catch (e: Exception) {
            viewModelScope.launch { _error.emit(e.message.toString()) }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository = (this[APPLICATION_KEY] as MyApp).repo
                AddWordViewModel(repo = myRepository)
            }
        }
    }
}