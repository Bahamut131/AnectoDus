package com.example.anectodus.domain.useCase.jokeUseCase

import com.example.anectodus.domain.repository.JokeRepository
import javax.inject.Inject

class GetJokeListForAccountPostUseCase @Inject constructor(val repository: JokeRepository) {
    operator fun invoke() = repository.getJokeListForAccountPost()
}