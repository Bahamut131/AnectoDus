package com.example.anectodus.domain.useCase.jokeUseCase

import com.example.anectodus.domain.entity.SomeJoke
import com.example.anectodus.domain.repository.JokeRepository
import javax.inject.Inject

class AddJokeUseCase @Inject constructor(private val jokeRepository: JokeRepository){

    suspend operator fun invoke(someJoke: SomeJoke){
        jokeRepository.addJoke(someJoke)
    }
}