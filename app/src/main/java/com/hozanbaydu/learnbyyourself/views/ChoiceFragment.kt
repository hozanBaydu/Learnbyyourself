package com.hozanbaydu.learnbyyourself.views

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hozanbaydu.learnbyyourself.R
import com.hozanbaydu.learnbyyourself.databinding.FragmentChoiceBinding
import com.hozanbaydu.learnbyyourself.databinding.FragmentWordBinding

class ChoiceFragment:Fragment(R.layout.fragment_choice) {

    private var fragmentBinding : FragmentChoiceBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentChoiceBinding.bind(view)
        fragmentBinding = binding


        binding.chooseImageview.setOnClickListener {

            findNavController().navigate(ChoiceFragmentDirections.actionChoiceFragmentToApiFragment())
        }

        binding.drawingImageview.setOnClickListener {
            findNavController().navigate(ChoiceFragmentDirections.actionChoiceFragmentToDrawingFragment())
        }

    }


    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

}