package com.example.anectodus.domain.useCase.jokeUseCase

import com.example.anectodus.domain.repository.JokeRepository
import javax.inject.Inject

class GetAccountListJokUseCase @Inject constructor(private val jokeRepository: JokeRepository) {

    operator fun invoke() =jokeRepository.getAccountJokeList()


}