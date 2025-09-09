package com.fwrdgrp.wordapp.ui.general

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.fwrdgrp.wordapp.MyApp
import com.fwrdgrp.wordapp.data.enums.Status
import com.fwrdgrp.wordapp.data.models.Word
import com.fwrdgrp.wordapp.data.repo.WordsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class WordDetailViewModel(
    private val repo: WordsRepo
) : ViewModel() {
    private val _finish = MutableSharedFlow<Unit>()
    val finish: SharedFlow<Unit> = _finish

    private var word: Word? = null

    //Gets word to populate the WordDetail
    suspend fun getWord(id: Int): Word {
        val result = repo.getWordById(id) ?: throw Exception("Word doesn't exist")
        word = result
        return result
    }

    //Changes Status
    fun changeStatus(word: Word) {
        viewModelScope.launch(Dispatchers.IO) {
            val newStatus = when (word.status) {
                Status.COMPLETE -> Status.INCOMPLETE
                Status.INCOMPLETE -> Status.COMPLETE
            }
            repo.editWord(word.copy(status = newStatus))
            _finish.emit(Unit)
        }
    }

    //Delete word
    fun deleteWord(wordId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteWord(wordId)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository = (this[APPLICATION_KEY] as MyApp).repo
                WordDetailViewModel(repo = myRepository)
            }
        }
    }
}