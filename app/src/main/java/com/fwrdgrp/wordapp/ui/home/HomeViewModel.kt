package com.fwrdgrp.wordapp.ui.home

import com.fwrdgrp.wordapp.data.enums.Status
import kotlinx.coroutines.flow.update

class HomeViewModel : BaseHomeViewModel() {
    init {
        getWords()
    }
    override fun getWords() {
        _words.update {
            //CompleteWord filters if the word has been "Not Done"
            repo.getWords().filter { it.status == Status.INCOMPLETE }
                .filter {
                    //This checks for a boolean, if currentSearch is blank, then it does nothing
                    //If it is not blank, it will filter according to the title, ignoring case
                    currentSearch.isBlank() || it.title.contains(currentSearch, ignoreCase = true)
                    //Below is the custom method.
                }.applySort(currentSort, currentOrder)
        }
    }


}