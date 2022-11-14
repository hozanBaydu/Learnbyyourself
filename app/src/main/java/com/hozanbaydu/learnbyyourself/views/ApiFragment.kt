package com.hozanbaydu.learnbyyourself.views

import android.app.ActionBar
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.hozanbaydu.learnbyyourself.R
import com.hozanbaydu.learnbyyourself.databinding.FragmentApiBinding
import com.hozanbaydu.learnbyyourself.util.Status
import com.hozanbaydu.learnbyyourself.viewmodel.WordViewModel
import javax.inject.Inject

class ApiFragment @Inject constructor(
    val glide: RequestManager
):Fragment(R.layout.fragment_api) {

    lateinit var wordViewModel: WordViewModel
    private var fragmentBinding : FragmentApiBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        wordViewModel = ViewModelProvider(requireActivity()).get(WordViewModel::class.java)

        val binding = FragmentApiBinding.bind(view)
        fragmentBinding = binding
        subscribeToObserver()

        binding.apiImageview.setOnClickListener {
            findNavController().navigate(ApiFragmentDirections.actionApiFragmentToImageFragment())

        }


        val callBack=object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()

            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callBack)



        binding.apiSaveButton.setOnClickListener {
            findNavController().navigate(ApiFragmentDirections.actionApiFragmentToWordFragment())
            wordViewModel.makeWord(binding.apiWordTextview.text.toString(),binding.apiSentenceTextview.text.toString())

        }

    }

    private fun subscribeToObserver(){
        wordViewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer {url ->
            fragmentBinding?.let {
                glide.load(url).into(it.apiImageview)  //secilen fotoyu ekrana getirmek için
            }
        })

        wordViewModel.insertArtMessage.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS ->{
                    Toast.makeText(requireContext(),"başarılı", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                    wordViewModel.resetInsertArtMsg()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message ?:"hata", Toast.LENGTH_SHORT).show()

                }
                Status.LOADING ->{}
            }
        })

    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}