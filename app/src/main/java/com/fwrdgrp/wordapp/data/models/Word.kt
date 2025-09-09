package com.fwrdgrp.wordapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fwrdgrp.wordapp.data.enums.Status
import java.util.Date

@Entity
data class Word(
    val title: String,
    val meaning: String,
    val synonym: String? = null,
    val details: String? = null,
    val status: Status = Status.INCOMPLETE,
    @PrimaryKey val id: Int? = null,
    val date: Date = Date()
)

