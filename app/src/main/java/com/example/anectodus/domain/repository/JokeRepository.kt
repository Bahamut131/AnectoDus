package com.example.anectodus.domain.repository

import androidx.lifecycle.LiveData
import com.example.anectodus.domain.entity.SomeJoke

interface JokeRepository {

    suspend fun addJoke(someJoke: SomeJoke)

    suspend fun deleteJoke(someJoke: SomeJoke)

    suspend fun getJoke(someJoke: SomeJoke) : SomeJoke

    fun getJokeList() : LiveData<List<SomeJoke>>



}