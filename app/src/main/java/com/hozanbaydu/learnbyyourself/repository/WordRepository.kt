package com.hozanbaydu.learnbyyourself.repository

import androidx.lifecycle.LiveData
import com.hozanbaydu.learnbyyourself.api.RetrofitApi
import com.hozanbaydu.learnbyyourself.model.Images
import com.hozanbaydu.learnbyyourself.roomdatabase.Word
import com.hozanbaydu.learnbyyourself.roomdatabase.WordDao
import com.hozanbaydu.learnbyyourself.util.Resource

import javax.inject.Inject


class WordRepository @Inject constructor(
    private val wordDao: WordDao,
    private val retrofitAPI: RetrofitApi
):WordRepositoryInterface {
    override suspend fun insertArt(word: Word) {
        wordDao.insertArt(word)
    }

    override suspend fun deleteArt(word: Word) {
        wordDao.deleteArt(word)
    }

    override fun getArt(): LiveData<List<Word>> {
        return wordDao.observeArt()
    }

    override suspend fun searchImage(imageString: String): Resource<Images> {
        return try {

            val response=retrofitAPI.imageSearch(imageString)
            if (response.isSuccessful){

                response.body()?.let {
                    return@let Resource.success(it)
                }?:Resource.error("error",null)
            }else{
                Resource.error("error",null)
            }

        }catch (e:Exception){
            Resource.error("no data",null)
        }
    }
}