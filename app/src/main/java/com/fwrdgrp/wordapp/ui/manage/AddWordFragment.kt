package com.fwrdgrp.wordapp.ui.manage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.fwrdgrp.wordapp.R
import com.fwrdgrp.wordapp.data.models.Word

class AddWordFragment : BaseManageFragment() {
    override val viewModel: AddWordViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            mbSubmit.text = getString(R.string.add)
            mbSubmit.setOnClickListener {
                viewModel.submit(
                    Word(
                        etTitle.text.toString(), etMeaning.text.toString(),
                        etSynonyms.text.toString(), etDetails.text.toString()
                    )
                )
            }
        }
    }
}