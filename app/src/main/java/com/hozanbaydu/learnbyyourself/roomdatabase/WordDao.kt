package com.hozanbaydu.learnbyyourself.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArt(art: Word)

    @Delete
    suspend fun deleteArt(art: Word)

    @Query("SELECT * FROM words")
    fun observeArt(): LiveData<List<Word>>
}