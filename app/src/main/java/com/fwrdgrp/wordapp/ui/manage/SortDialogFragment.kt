package com.fwrdgrp.wordapp.ui.manage

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.fwrdgrp.wordapp.R
import com.fwrdgrp.wordapp.data.enums.SortBy
import com.fwrdgrp.wordapp.data.enums.SortOrder
import com.fwrdgrp.wordapp.databinding.SortPopupBinding

class SortDialogFragment(
    private val currentSort: SortBy,
    private val currentOrder: SortOrder,
    private val onSortClick: (SortBy, SortOrder) -> Unit
) : DialogFragment() {

    private lateinit var binding: SortPopupBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //Inflates the XML for the SortDialogFragment
        binding = SortPopupBinding.inflate(layoutInflater)
        setRadio(currentSort, currentOrder)
        val dialog = Dialog(requireContext())
        dialog.setContentView(binding.root)
        //Declares the new value changes
        binding.mbDone.setOnClickListener {
            val sortBy = when (binding.rgSort.checkedRadioButtonId) {
                R.id.rbTitle -> SortBy.TITLE
                else -> SortBy.DATE
            }
            val sortOrder = when (binding.rgOrder.checkedRadioButtonId) {
                R.id.rbAscending -> SortOrder.ASCENDING
                else -> SortOrder.DESCENDING
            }
            //Sends the information back to lambda
            onSortClick(sortBy, sortOrder)
            dismiss()
        }
        return dialog
    }

    //This sets the checked values of the correct radio button
    fun setRadio(currentSort: SortBy, currentOrder: SortOrder) {
        when (currentSort) {
            SortBy.TITLE -> binding.rbTitle.isChecked = true
            SortBy.DATE -> binding.rbDate.isChecked = true
        }
        when (currentOrder) {
            SortOrder.ASCENDING -> binding.rbAscending.isChecked = true
            SortOrder.DESCENDING -> binding.rbDescending.isChecked = true
        }
    }
}