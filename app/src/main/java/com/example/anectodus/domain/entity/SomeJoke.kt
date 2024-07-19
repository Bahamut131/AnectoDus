package com.example.anectodus.domain.entity

import com.google.firebase.Timestamp


data class SomeJoke(
    var id : String= "",
    var text : String = "",
    var idFromUser : String = "",
    val userLikePost: List<String> ?= null,
    val timestamp: Timestamp ?= null,
    var likeJoke : Boolean = false
)
{
    companion object{
        const val ID = 0
    }

}