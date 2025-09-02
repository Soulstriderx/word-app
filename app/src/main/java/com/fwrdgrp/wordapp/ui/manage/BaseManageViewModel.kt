package com.fwrdgrp.wordapp.ui.manage

import androidx.lifecycle.ViewModel
import com.fwrdgrp.wordapp.data.models.Word
import com.fwrdgrp.wordapp.data.repo.WordsRepo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

abstract class BaseManageViewModel(
    protected val repo: WordsRepo = WordsRepo.getInstance()
) : ViewModel() {
    protected val _finish = MutableSharedFlow<Unit>()
    val finish: SharedFlow<Unit> = _finish
    protected val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error
    abstract fun submit(word: Word)
}