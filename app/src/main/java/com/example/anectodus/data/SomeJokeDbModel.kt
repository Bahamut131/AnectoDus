package com.example.anectodus.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "some_joke")
data class SomeJokeDbModel(
    @PrimaryKey(true)
    var id : Int,
    var text : String,
    var likeJoke : Boolean
)

