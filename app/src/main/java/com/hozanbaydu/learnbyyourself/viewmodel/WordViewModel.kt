package com.hozanbaydu.learnbyyourself.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hozanbaydu.learnbyyourself.model.Images
import com.hozanbaydu.learnbyyourself.repository.WordRepositoryInterface
import com.hozanbaydu.learnbyyourself.roomdatabase.Word
import com.hozanbaydu.learnbyyourself.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(
    private val repository:WordRepositoryInterface

): ViewModel() {


    val artList=repository.getArt()



    private val images= MutableLiveData<Resource<Images>>()

    val imageList : LiveData<Resource<Images>>
        get() = images

    private val selectedImage= MutableLiveData<String>()
    val selectedImageUrl: LiveData<String>
        get() = selectedImage

    //ArtDetailsFragment

    private var insertArtMsg= MutableLiveData<Resource<Word>>()
    val insertArtMessage: LiveData<Resource<Word>>
        get() = insertArtMsg

    fun resetInsertArtMsg(){

        insertArtMsg= MutableLiveData<Resource<Word>>()

    }

    fun setSelectedImage(url : String) {
        selectedImage.postValue(url)
    }

    fun deleteArt(word: Word) = viewModelScope.launch {
        repository.deleteArt(word)
    }

    fun insertArt(word: Word) = viewModelScope.launch {
        repository.insertArt(word)
    }


    fun makeWord(name : String, artistName : String) {
        if (name.isEmpty() || artistName.isEmpty()  ) {
            insertArtMsg.postValue(Resource.error("Lütfen kelime girin,Lütfen cümle girin ", null))
            return
        }


        val word = Word(name, artistName,selectedImage.value?: "")
        insertArt(word)
        setSelectedImage("")
        insertArtMsg.postValue(Resource.success(word))
    }

    fun searchForImage (searchString : String) {

        if(searchString.isEmpty()) {
            return
        }
        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.searchImage(searchString)
            images.value = response
        }
    }



    var word=""

    var sentence=""

    var ımageUrl=""





}