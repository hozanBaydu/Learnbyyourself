package com.hozanbaydu.learnbyyourself.repository

import androidx.lifecycle.LiveData
import com.hozanbaydu.learnbyyourself.model.ImageModel
import com.hozanbaydu.learnbyyourself.model.Images
import com.hozanbaydu.learnbyyourself.roomdatabase.Word
import com.hozanbaydu.learnbyyourself.util.Resource

interface WordRepositoryInterface {

    suspend fun insertArt(word: Word)
    suspend fun deleteArt(word: Word)
    fun getArt(): LiveData<List<Word>>
    suspend fun searchImage(imageString: String): Resource<Images>
}