package com.example.anectodus.domain.useCase.jokeUseCase

import com.example.anectodus.domain.entity.SomeJoke
import com.example.anectodus.domain.repository.JokeRepository
import javax.inject.Inject

class LikeJokeUseCase @Inject constructor(val repository: JokeRepository) {
    suspend operator fun invoke(joke: SomeJoke){
        repository.likeJoke(joke)
    }
}