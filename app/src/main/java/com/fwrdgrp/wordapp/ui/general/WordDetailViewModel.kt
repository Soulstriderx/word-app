package com.fwrdgrp.wordapp.ui.general

import androidx.lifecycle.ViewModel
import com.fwrdgrp.wordapp.data.enums.Status
import com.fwrdgrp.wordapp.data.models.Word
import com.fwrdgrp.wordapp.data.repo.WordsRepo

class WordDetailViewModel(
    private val repo: WordsRepo = WordsRepo.getInstance()
) : ViewModel() {
    private var word: Word? = null

    fun getWord(id: Int): Word {
        repo.getWordById(id)?.let {
            word = it
        }
        return this.word ?: throw Exception("Word doesn't exist")
    }

    fun changeStatus(word: Word) {
        val newStatus = when (word.status) {
            Status.COMPLETE -> Status.INCOMPLETE
            Status.INCOMPLETE -> Status.COMPLETE
        }
        repo.updateWord(word.id!!, word.copy(status = newStatus))
    }

    fun deleteWord(wordId: Int) {
        repo.deleteWord(wordId)
    }
}