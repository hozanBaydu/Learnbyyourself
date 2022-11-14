package com.hozanbaydu.learnbyyourself.roomdatabase

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "words")
data class Word(
    var name:String,
    var sentence:String,
    var imageUrl:String,
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null
)
