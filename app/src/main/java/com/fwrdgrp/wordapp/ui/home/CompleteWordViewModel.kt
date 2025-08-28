package com.fwrdgrp.wordapp.ui.home

import com.fwrdgrp.wordapp.data.models.Status
import kotlinx.coroutines.flow.update

class CompleteWordViewModel : BaseHomeViewModel() {
    init {
        getWords()
    }

    override fun getWords() {
        _words.update {
            repo.getWords().filter { it.status == Status.COMPLETE }
        }
    }

}