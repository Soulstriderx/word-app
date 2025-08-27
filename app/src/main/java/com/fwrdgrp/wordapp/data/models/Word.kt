package com.fwrdgrp.wordapp.data.models

data class Word(
    val title: String,
    val meaning: String,
    val synonym: String? = null,
    val details: String? = null,
    val status: Status = Status.INCOMPLETE,
    val id: Int? = null


)

enum class Status{
    COMPLETE, INCOMPLETE
}
