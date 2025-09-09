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

class EditWordViewModel(
    repo: WordsRepo
) : BaseManageViewModel(repo) {
    private var word: Word? = null

    //Gets word to populate the EditWord's EditText fields
    suspend fun getWord(id: Int): Word {
        val result = repo.getWordById(id) ?: throw Exception("Word doesn't exist")
        word = result
        return result
    }

    //Submits to repo.
    override fun submit(newWord: Word) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                require(newWord.title.isNotBlank()) { "NO_TITLE" }
                require(newWord.meaning.isNotBlank()) { "NO_MEANING" }
                word?.let {
                    repo.editWord(
                        it.copy(newWord.title, newWord.meaning, newWord.synonym, newWord.details)
                    )
                }
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
                EditWordViewModel(repo = myRepository)
            }
        }
    }
}