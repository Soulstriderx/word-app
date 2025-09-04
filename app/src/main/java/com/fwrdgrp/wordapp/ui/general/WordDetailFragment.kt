package com.fwrdgrp.wordapp.ui.general

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fwrdgrp.wordapp.R
import com.fwrdgrp.wordapp.data.repo.WordsRepo
import com.fwrdgrp.wordapp.databinding.FragmentWordDetailBinding
import com.google.android.material.button.MaterialButton
import androidx.core.graphics.drawable.toDrawable
import com.fwrdgrp.wordapp.data.enums.Status
import com.fwrdgrp.wordapp.data.models.Word


class WordDetailFragment : Fragment() {

    private val repo: WordsRepo = WordsRepo.getInstance()
    private lateinit var binding: FragmentWordDetailBinding
    private val args: WordDetailFragmentArgs by navArgs()
    private lateinit var word: Word

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWordDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        word = repo.getWordById(args.wordId) ?: throw Exception("Word does not exist")
        setData(word)

        setFragmentResultListener("manage_edit_word", { _, _ -> getWord() })
    }

    fun setData(word: Word) {
        binding.run {
            mtDetails.setNavigationOnClickListener { findNavController().popBackStack() }
            tvTitle.text = word.title
            tvMeaning.text = word.meaning
            tvSynonym.text = word.synonym ?: "There are currently no Synonyms for this word"
            tvDetails.text = word.details ?: "There are currently no details available"
            mbDone.setOnClickListener {
                val dialog = createCompletedDialog()
                dialog.show()
            }
            mbDelete.setOnClickListener {
                val dialog = createDialog(args.wordId)
                dialog.show()
            }
            mbUpdate.setOnClickListener {
                val action = WordDetailFragmentDirections.actionWordDetailToEditWord(word.id!!)
                findNavController().navigate(action)
            }
        }
    }

    fun getWord() {
        val newWord = repo.getWordById(args.wordId) ?: throw Exception("This word doesn't exist")
        word = newWord
        binding.run {
            tvTitle.text = word.title
            tvMeaning.text = word.meaning
            tvSynonym.text = word.synonym ?: "There are currently no Synonyms for this word"
            tvDetails.text = word.details ?: "There are currently no details available"
        }
    }

    fun setStatus() {
        repo.updateWord(word.id!!, word.copy(status = Status.COMPLETE))
        getWord()
        setFragmentResult("manage_word", Bundle())
    }

    fun createCompletedDialog(): Dialog{
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.confirmation_dialog)
        dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        val tvTitle = dialog.findViewById<TextView>(R.id.tvConfirm)
        tvTitle.text = "Do you want to move the word to complete list?"
        val mbNo = dialog.findViewById<MaterialButton>(R.id.mbCancel)
        mbNo.text = "No"
        mbNo.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.teal))
        val mbYes = dialog.findViewById<MaterialButton>(R.id.mbDelete)
        mbYes.text = "Yes"
        mbYes.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green))
        mbYes.setOnClickListener {
            setStatus()
            dialog.dismiss()
        }
        mbNo.setOnClickListener {
            dialog.dismiss()
        }
        return dialog
    }

    fun createDialog(wordId: Int): Dialog {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.confirmation_dialog)
        dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        val mbCancel = dialog.findViewById<MaterialButton>(R.id.mbCancel)
        val mbDelete = dialog.findViewById<MaterialButton>(R.id.mbDelete)
        mbCancel.setOnClickListener {
            dialog.dismiss()
        }
        mbDelete.setOnClickListener {
            repo.deleteWord(wordId)
            setFragmentResult("manage_word", Bundle())
            findNavController().popBackStack()
            dialog.dismiss()
        }
        return dialog
    }
}