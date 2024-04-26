package com.example.anectodus.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "likes_joke_in_account")
data class LikesJokeInAccount (
    @PrimaryKey(true)
    var id : Int,
    var text : String,
    var likeJoke : Boolean
)
