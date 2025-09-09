package com.fwrdgrp.wordapp.ui.manage

import androidx.lifecycle.viewModelScope
import com.fwrdgrp.wordapp.data.models.Word
import kotlinx.coroutines.launch

class EditWordViewModel : BaseManageViewModel() {
    private var word: Word? = null

    //Gets word to populate the EditWord's EditText fields
    fun getWord(id: Int): Word {
        repo.getWordById(id)?.let {
            word = it
        }
        return this.word ?: throw Exception("Word doesn't exist")
    }

    //Submits to repo.
    override fun submit(newWord: Word) {
        try {
            require(newWord.title.isNotBlank()) { "NO_TITLE" }
            require(newWord.meaning.isNotBlank()) { "NO_MEANING" }
            word?.let {
                repo.updateWord(
                    it.id!!, it.copy(
                        newWord.title, newWord.meaning, newWord.synonym, newWord.details
                    )
                )
            }
            viewModelScope.launch {
                _finish.emit(Unit)
            }
        } catch (e: Exception) {
            viewModelScope.launch { _error.emit(e.message.toString()) }
        }
    }
}