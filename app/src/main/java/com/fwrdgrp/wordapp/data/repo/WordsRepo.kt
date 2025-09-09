package com.fwrdgrp.wordapp.data.repo

import com.fwrdgrp.wordapp.data.models.Word
import com.fwrdgrp.wordapp.database.WordsDao
import kotlinx.coroutines.flow.Flow

class WordsRepo(
    private val dao: WordsDao
) {
    fun addWord(product: Word) {
        dao.addWord(product)
    }

    fun getAllWords(): Flow<List<Word>> {
        return dao.getAllWords()
    }

    suspend fun getWordById(id: Int): Word? {
        return dao.getWordById(id)
    }

    fun editWord(product: Word) {
        dao.update(product)
    }

    fun deleteWord(id: Int) {
        dao.delete(id)
    }
}