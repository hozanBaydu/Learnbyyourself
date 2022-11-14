package com.hozanbaydu.learnbyyourself.views

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.hozanbaydu.learnbyyourself.R
import com.hozanbaydu.learnbyyourself.adapter.WordRecyclerAdapter
import com.hozanbaydu.learnbyyourself.databinding.FragmentDetailsBinding
import com.hozanbaydu.learnbyyourself.databinding.FragmentWordBinding
import com.hozanbaydu.learnbyyourself.viewmodel.WordViewModel
import javax.inject.Inject

class DetailsFragment @Inject constructor(
    val wordRecyclerAdapter: WordRecyclerAdapter,
    val glide : RequestManager
):Fragment(R.layout.fragment_details) {

    private var fragmentBinding : FragmentDetailsBinding? = null
    lateinit var viewModel: WordViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(requireActivity()).get(WordViewModel::class.java)

        val binding = FragmentDetailsBinding.bind(view)
        fragmentBinding = binding

        binding.detailsWord.text=viewModel.word
        binding.detailsSentence.text=viewModel.sentence

        glide.load(viewModel.ımageUrl).into(binding.detailsImageview)


    }
}