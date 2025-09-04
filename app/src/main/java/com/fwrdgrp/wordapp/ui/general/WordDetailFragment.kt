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
import com.fwrdgrp.wordapp.databinding.FragmentWordDetailBinding
import com.google.android.material.button.MaterialButton
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.viewModels
import com.fwrdgrp.wordapp.data.enums.Status
import com.fwrdgrp.wordapp.data.models.Word
import com.fwrdgrp.wordapp.data.util.Constant


class WordDetailFragment : Fragment() {
    private val viewModel: WordDetailViewModel by viewModels()
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
        word = viewModel.getWord(args.wordId)
        setData(word)

        setFragmentResultListener(Constant.MANAGE_EDIT_WORD, { _, _ -> getWord() })
    }

    fun setData(word: Word?) {
        binding.run {
            mtDetails.setNavigationOnClickListener { findNavController().popBackStack() }
            tvTitle.text = word?.title
            tvMeaning.text = word?.meaning
            tvSynonym.text = word?.synonym ?: getString(R.string.no_synonym)
            tvDetails.text = word?.details ?: getString(R.string.no_detail)
        }
        setOnClickListeners()
    }

    fun setOnClickListeners() {
        binding.run {
            mbDone.text = if (word.status == Status.COMPLETE) getString(R.string.undone)
            else getString(R.string.done)
            mbDone.setOnClickListener {
                val dialog = createCompletedDialog()
                dialog.show()
            }
            mbConfirm.setOnClickListener {
                val dialog = createDeleteDialog(args.wordId)
                dialog.show()
            }
            mbUpdate.setOnClickListener {
                val action = WordDetailFragmentDirections.actionWordDetailToEditWord(args.wordId)
                findNavController().navigate(action)
            }
        }
    }

    fun getWord() {
        val newWord = viewModel.getWord(args.wordId)
        word = newWord
        setData(newWord)
    }

    fun setStatus() {
        viewModel.changeStatus(word)
        getWord()
        setFragmentResult(Constant.MANAGE_WORD, Bundle())
    }

    fun createCompletedDialog(): Dialog {
        return Dialog(requireContext()).apply {
            setContentView(R.layout.confirmation_dialog)
            window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
            findViewById<TextView>(R.id.tvConfirm).text = if (word.status == Status.INCOMPLETE) {
                getString(R.string.to_complete_question)
            } else {
                getString(R.string.to_new_question)
            }
            findViewById<MaterialButton>(R.id.mbCancel).apply {
                text = getString(R.string.no)
                setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.teal))
                setOnClickListener { dismiss() }
            }
            findViewById<MaterialButton>(R.id.mbConfirm).apply {
                text = getString(R.string.yes)
                setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green))
                setOnClickListener { setStatus(); dismiss() }
            }
        }
    }

    fun createDeleteDialog(wordId: Int): Dialog {
        return Dialog(requireContext()).apply {
            setContentView(R.layout.confirmation_dialog)
            window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
            findViewById<MaterialButton>(R.id.mbCancel).setOnClickListener { dismiss() }
            findViewById<MaterialButton>(R.id.mbConfirm).setOnClickListener {
                viewModel.deleteWord(wordId)
                setFragmentResult(Constant.MANAGE_WORD, Bundle())
                findNavController().popBackStack()
                dismiss()
            }
        }
    }
}