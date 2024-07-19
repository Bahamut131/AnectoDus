package com.example.anectodus.domain.repository

import com.example.anectodus.domain.entity.SomeJoke
import kotlinx.coroutines.flow.Flow

interface JokeRepository {


    suspend fun likeJoke(joke: SomeJoke)

    suspend fun addJoke(jokeText: String)

    suspend fun deleteJoke(someJoke: SomeJoke)

    fun getJokeList(lastVisibility : SomeJoke?) : Flow<List<SomeJoke>>
    fun getJokeListForAccountPost() : Flow<List<SomeJoke>>
    fun getJokeListForAccountLike() : Flow<List<SomeJoke>>




}