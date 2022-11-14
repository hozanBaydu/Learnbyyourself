package com.hozanbaydu.learnbyyourself.roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [Word::class], version = 1)
abstract class WordDatabase:RoomDatabase(){

    abstract fun wordDao():WordDao
}
