package com.fwrdgrp.wordapp.data.repo

import com.fwrdgrp.wordapp.data.models.Word

class WordsRepo private constructor() {
    val map = mutableMapOf<Int, Word>()
    var counter = 0

    init {
        createRandomWords(10)
    }
    fun add(word: Word) {
        counter++
        map[counter] = word.copy(id = counter)
    }

    fun getWordById(id: Int): Word? {
        return map[id]
    }

    fun getWords() = map.values.toList()

    fun deleteWord(id: Int) {
        map.remove(id)
    }

    fun updateWord(id: Int, word: Word) {
        map[id] = word
    }

    fun createRandomWords(n: Int) {
        repeat(n) {
            map[++counter] = Word(title = "Title $it", meaning = "Meaning $it", id = it + 1)
        }
    }

    companion object {
        private var instance: WordsRepo? = null

        fun getInstance(): WordsRepo {
            if (instance == null) {
                instance = WordsRepo()
            }
            return instance!!
        }
    }
}