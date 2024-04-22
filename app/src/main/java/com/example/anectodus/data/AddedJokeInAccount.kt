package com.example.anectodus.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "added_joke_in_account")
class AddedJokeInAccount (
    @PrimaryKey(true)
    var id : Int,
    var text : String,
    var likeJoke : Boolean
)