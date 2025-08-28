package com.fwrdgrp.wordapp.ui.home

import androidx.lifecycle.ViewModel
import com.fwrdgrp.wordapp.data.models.Word
import com.fwrdgrp.wordapp.data.repo.WordsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseHomeViewModel(
    protected val repo: WordsRepo = WordsRepo.getInstance()
) : ViewModel() {
    protected val _words = MutableStateFlow<List<Word>>(emptyList())
    val words: StateFlow<List<Word>> = _words

    abstract fun getWords()

    fun refresh() {
        getWords()
    }
}