package com.fwrdgrp.wordapp.data.models

import com.fwrdgrp.wordapp.data.enums.Status
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

