package com.hozanbaydu.learnbyyourself.dependencyinjections

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hozanbaydu.learnbyyourself.R
import com.hozanbaydu.learnbyyourself.api.RetrofitApi
import com.hozanbaydu.learnbyyourself.repository.WordRepository
import com.hozanbaydu.learnbyyourself.repository.WordRepositoryInterface
import com.hozanbaydu.learnbyyourself.roomdatabase.WordDao
import com.hozanbaydu.learnbyyourself.roomdatabase.WordDatabase
import com.hozanbaydu.learnbyyourself.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun injectRoomDatabase(    //fonksiyon ismi önemli değil.
        @ApplicationContext context: Context
    )= Room.databaseBuilder(
        context,WordDatabase::class.java,"ArtBookDataBase"
    ).build()

    @Singleton
    @Provides
    fun injectDao(dataBase: WordDatabase)=dataBase.wordDao()

    @Singleton
    @Provides

    fun injectRetrofitAPI() : RetrofitApi { //modülde süslü parantez varsa return demeliyiz.
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RetrofitApi::class.java)
    }

    @Singleton
    @Provides
    fun injectNormalRepo(dao: WordDao,api: RetrofitApi)=WordRepository(dao,api) as WordRepositoryInterface





    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context)= Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_foreground)

                .error(R.drawable.ic_launcher_foreground)
        )
}