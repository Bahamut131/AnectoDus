package com.example.anectodus.domain.useCase.jokeUseCase

import com.example.anectodus.domain.entity.SomeJoke
import com.example.anectodus.domain.repository.JokeRepository
import javax.inject.Inject

class DeleteAccountJokeUseCase @Inject constructor(private val repository: JokeRepository){

    suspend operator fun invoke(someJoke: SomeJoke){
        repository.deleteAccountJoke(someJoke)
    }

}