package com.fwrdgrp.wordapp.ui.general

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.fwrdgrp.wordapp.R
import com.fwrdgrp.wordapp.data.repo.WordsRepo
import com.fwrdgrp.wordapp.databinding.FragmentWordDetailBinding


class WordDetailFragment : Fragment() {

    private val repo: WordsRepo = WordsRepo.getInstance()
    private lateinit var binding: FragmentWordDetailBinding
//    private val args: WordDetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWordDetailBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val word = repo.getWordById(args.wordId)
        binding.run {
//            tvTitle.setText(word.title)
//            tvMeaning.setText(word.meaning)
//            tvSynonym.setText(word.synonym)
//            tvDetails.setText(word.details)
        }
    }
}