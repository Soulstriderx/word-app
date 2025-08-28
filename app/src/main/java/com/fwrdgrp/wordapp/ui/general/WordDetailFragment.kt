package com.fwrdgrp.wordapp.ui.general

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fwrdgrp.wordapp.R
import com.fwrdgrp.wordapp.data.models.Word
import com.fwrdgrp.wordapp.data.repo.WordsRepo
import com.fwrdgrp.wordapp.databinding.FragmentWordDetailBinding
import com.google.android.material.button.MaterialButton


class WordDetailFragment : Fragment() {

    private val repo: WordsRepo = WordsRepo.getInstance()
    private lateinit var binding: FragmentWordDetailBinding
    private val args: WordDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWordDetailBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val word = repo.getWordById(args.wordId)

        binding.run {
            mtDetails.setNavigationOnClickListener { findNavController().popBackStack() }
            tvTitle.text = word?.title
            tvMeaning.text = word?.meaning
            tvSynonym.text = word?.synonym ?: "There are currently no Synonyms for this word"
            tvDetails.text = word?.details ?: "There are currently no details available"
            mbDelete.setOnClickListener {
                val dialog = createDialog(requireContext(),args.wordId)
                dialog.show()
            }
        }
    }

    fun createDialog(context: Context, wordId: Int): Dialog{
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.confirmation_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val mbCancel = dialog.findViewById<MaterialButton>(R.id.mbCancel)
        val mbDelete = dialog.findViewById<MaterialButton>(R.id.mbDelete)
        mbCancel.setOnClickListener {
            dialog.dismiss()
        }
        mbDelete.setOnClickListener {
            repo.deleteWord(wordId)
            dialog.dismiss()
        }
        return dialog
    }
}