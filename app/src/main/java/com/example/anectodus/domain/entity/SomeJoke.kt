package com.example.anectodus.domain.entity

import java.security.Timestamp

data class SomeJoke(
    var id : Int= ID,
    var text : String = "",
    var idFromUser : String = "",
    var likeJoke : Boolean = false
    )
{
    companion object{
        const val ID = 0
    }
}