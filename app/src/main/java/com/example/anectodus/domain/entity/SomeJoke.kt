package com.example.anectodus.domain.entity

data class SomeJoke(
    var id : Int= ID,
    var text : String,
    var likeJoke : Boolean = false
    ) {
    companion object{
        const val ID = 0
    }
}