package com.example.anectodus.domain.useCase

import com.example.anectodus.domain.entity.SomeJoke
import com.example.anectodus.domain.repository.JokeRepository
import javax.inject.Inject

class GetJokeUseCase @Inject constructor(private val jokeRepository: JokeRepository) {

    suspend operator fun invoke(somejoke : SomeJoke) : SomeJoke{
        return jokeRepository.getJoke(somejoke)
    }

}