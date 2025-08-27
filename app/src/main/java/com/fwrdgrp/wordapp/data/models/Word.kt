package com.fwrdgrp.wordapp.data.models

import java.util.Date

data class Word(
    val title: String,
    val meaning: String,
    val synonym: String? = null,
    val details: String? = null,
    val status: Status = Status.INCOMPLETE,
    val id: Int? = null,
    val date: Date = Date()
)
enum class Status{
    COMPLETE, INCOMPLETE
}
