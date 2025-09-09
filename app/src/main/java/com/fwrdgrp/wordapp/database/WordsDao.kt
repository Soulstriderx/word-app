package com.fwrdgrp.wordapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.fwrdgrp.wordapp.data.models.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordsDao {
    @Query("SELECT * FROM word")
    fun getAllWords(): Flow<List<Word>>

    @Query("SELECT * FROM word WHERE id = :id")
    suspend fun getWordById(id: Int): Word?

    @Insert
    fun addWord(product: Word)

    @Update
    fun update(product: Word)

    @Query("DELETE FROM word WHERE id = :id")
    fun delete(id: Int)

}