package com.fwrdgrp.wordapp.ui.manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fwrdgrp.wordapp.data.models.Word
import com.fwrdgrp.wordapp.data.repo.WordsRepo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class AddWordViewModel(
    private val repo: WordsRepo = WordsRepo.getInstance()
) : ViewModel() {
    private val _finish = MutableSharedFlow<Unit>()
    val finish: SharedFlow<Unit> = _finish
    private val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error
    fun addWord(title: String, meaning: String, synonym: String, details: String) {
        try {
            require(title.isNotBlank()) { "Title cannot be blank" }
            require(meaning.isNotBlank()) { "Meaning cannot be blank" }
            require(synonym.isNotBlank()) { "Synonym cannot be blank" }
            require(details.isNotBlank()) { "Detail cannot be blank" }

            val word = Word(title = title, meaning = meaning, synonym = synonym, details = details)
            repo.add(word)
            viewModelScope.launch {
                _finish.emit(Unit)
            }
        } catch (e: Exception) {
            viewModelScope.launch { _error.emit(e.message.toString()) }
        }
    }
}