package com.fwrdgrp.wordapp

import android.app.Application
import androidx.room.Room
import com.fwrdgrp.wordapp.data.repo.WordsRepo
import com.fwrdgrp.wordapp.database.MyDatabase

class MyApp : Application() {
    lateinit var repo: WordsRepo
    override fun onCreate() {
        super.onCreate()

        val db = Room.databaseBuilder(
            this,
            MyDatabase::class.java,
            MyDatabase.NAME
        ).build()
        repo = WordsRepo(db.getWordsDao())
    }
}