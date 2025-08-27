package com.fwrdgrp.wordapp.ui.home

import androidx.lifecycle.ViewModel
import com.fwrdgrp.wordapp.data.models.Word
import com.fwrdgrp.wordapp.data.repo.WordsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel(
    private val repo: WordsRepo = WordsRepo.getInstance()
) : ViewModel() {
    private val _words = MutableStateFlow<List<Word>>(emptyList())
    val words: StateFlow<List<Word>> = _words

    init {
        getWords()
    }

    fun getWords() {
        _words.update { repo.getWords() }
    }

    fun refresh() {
        getWords()
    }

    fun deleteWord(word: Word) {
        repo.deleteWord(word.id!!)
        refresh()
    }
}