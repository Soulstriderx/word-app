package com.fwrdgrp.wordapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fwrdgrp.wordapp.data.models.Word
import com.fwrdgrp.wordapp.data.util.Converters

@Database(entities = [Word::class], version = 1)
@TypeConverters(Converters::class)
abstract class MyDatabase: RoomDatabase() {
    abstract fun getWordsDao(): WordsDao

    companion object {
        const val NAME = "my_database"
    }
}