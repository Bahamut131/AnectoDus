package com.example.anectodus.domain.repository

import com.example.anectodus.domain.entity.SomeJoke
import kotlinx.coroutines.flow.Flow

interface JokeRepository {

    suspend fun addJoke(someJoke: SomeJoke)

    suspend fun deleteJoke(someJoke: SomeJoke)

    fun getJokeList() : Flow<List<SomeJoke>>
    fun getJokeListForAccountPost() : Flow<List<SomeJoke>>




}