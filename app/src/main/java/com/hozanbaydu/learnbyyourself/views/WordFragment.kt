package com.hozanbaydu.learnbyyourself.views

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hozanbaydu.learnbyyourself.R
import com.hozanbaydu.learnbyyourself.adapter.WordRecyclerAdapter
import com.hozanbaydu.learnbyyourself.databinding.FragmentWordBinding
import com.hozanbaydu.learnbyyourself.viewmodel.WordViewModel
import javax.inject.Inject

class WordFragment @Inject constructor(

    val wordRecyclerAdapter: WordRecyclerAdapter,


    ):Fragment(R.layout.fragment_word) {

    private var fragmentBinding : FragmentWordBinding? = null
    lateinit var viewModel: WordViewModel
    private val swipeCallBack=object : ItemTouchHelper.SimpleCallback(0,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition=viewHolder.layoutPosition
            val selectedArt=wordRecyclerAdapter.words[layoutPosition]
            viewModel.deleteArt(selectedArt)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(WordViewModel::class.java)


        val binding = FragmentWordBinding.bind(view)
        fragmentBinding = binding

        subscribeToObserve()

        binding.wordRecyclerview.adapter=wordRecyclerAdapter
        binding.wordRecyclerview.layoutManager= LinearLayoutManager(requireContext())

        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.wordRecyclerview)

        binding.addWordActionButton.setOnClickListener {
            findNavController().navigate(WordFragmentDirections.actionWordFragmentToChoiceFragment())
        }

        wordRecyclerAdapter.setOnItemClickListener { word, sentence, url ->

            viewModel.word=word
            viewModel.sentence=sentence
            viewModel.ımageUrl=url

            findNavController().navigate(WordFragmentDirections.actionWordFragmentToDetailsFragment())
        }

    }

    private fun subscribeToObserve(){

        viewModel.artList.observe(viewLifecycleOwner, Observer {
            wordRecyclerAdapter.words=it
        })
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}