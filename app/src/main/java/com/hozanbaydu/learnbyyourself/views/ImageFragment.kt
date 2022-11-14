package com.hozanbaydu.learnbyyourself.views

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.hozanbaydu.learnbyyourself.R
import com.hozanbaydu.learnbyyourself.adapter.ApiRecyclerAdapter
import com.hozanbaydu.learnbyyourself.databinding.FragmentImageBinding
import com.hozanbaydu.learnbyyourself.util.Status
import com.hozanbaydu.learnbyyourself.viewmodel.WordViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageFragment @Inject constructor(
    val apiRecyclerAdapter: ApiRecyclerAdapter
):Fragment(R.layout.fragment_image) {

    lateinit var wordViewModel: WordViewModel
    private var fragmentBinding : FragmentImageBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wordViewModel = ViewModelProvider(requireActivity()).get(WordViewModel::class.java)
        val binding = FragmentImageBinding.bind(view)
        fragmentBinding = binding


        var job: Job?=null
        binding.searchText.addTextChangedListener {
            job?.cancel()
            job=lifecycleScope.launch {
                delay(100)
                it?.let {
                    if(it.toString().isNotEmpty()){
                        wordViewModel.searchForImage(it.toString())
                    }
                }
            }

        }

        subscribeToObserver()

        binding.imageRecyclerview.adapter=apiRecyclerAdapter
        binding.imageRecyclerview.layoutManager= GridLayoutManager(requireContext(),4)
        apiRecyclerAdapter.setOnItemClickListener {
            findNavController().popBackStack() //Geri gitmek için.
            wordViewModel.setSelectedImage(it)

        }


    }

    fun subscribeToObserver(){
        wordViewModel.imageList.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    val urls=it.data?.hits?.map {
                        it.previewURL
                    }

                    apiRecyclerAdapter.images=urls ?: listOf()
                    fragmentBinding?.progressBar?.visibility= View.GONE

                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message ?:"error", Toast.LENGTH_SHORT).show()
                    fragmentBinding?.progressBar?.visibility= View.GONE
                }
                Status.LOADING -> {
                    fragmentBinding?.progressBar?.visibility= View.VISIBLE
                }
            }


        })
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}