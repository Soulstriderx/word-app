package com.fwrdgrp.wordapp.ui.manage

import androidx.lifecycle.viewModelScope
import com.fwrdgrp.wordapp.data.models.Word
import kotlinx.coroutines.launch

class AddWordViewModel: BaseManageViewModel() {

    override fun submit(word: Word) {
        try {
            require(word.title.isNotBlank()) { "NO_TITLE" }
            require(word.meaning.isNotBlank()) { "NO_MEANING" }
            repo.add(word)
            viewModelScope.launch {
                _finish.emit(Unit)
            }
        } catch (e: Exception) {
            viewModelScope.launch { _error.emit(e.message.toString()) }
        }
    }
}