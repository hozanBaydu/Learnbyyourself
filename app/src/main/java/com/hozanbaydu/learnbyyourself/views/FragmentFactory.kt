package com.hozanbaydu.learnbyyourself.views

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.hozanbaydu.learnbyyourself.adapter.ApiRecyclerAdapter
import com.hozanbaydu.learnbyyourself.adapter.WordRecyclerAdapter
import javax.inject.Inject

class FragmentFactory @Inject constructor(
    private val wordRecyclerAdapter: WordRecyclerAdapter,
    private val glide : RequestManager,
    private val apiRecyclerAdapter: ApiRecyclerAdapter
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className){
            ImageFragment::class.java.name -> ImageFragment(apiRecyclerAdapter)
            WordFragment::class.java.name -> WordFragment(wordRecyclerAdapter)
            ApiFragment::class.java.name -> ApiFragment(glide)
            DetailsFragment::class.java.name -> DetailsFragment(wordRecyclerAdapter,glide)
            else -> return super.instantiate(classLoader, className)
        }
    }
}