package com.example.anectodus.domain.useCase

import com.example.anectodus.domain.repository.JokeRepository
import javax.inject.Inject

class GetListJokUseCase @Inject constructor(private val jokeRepository: JokeRepository) {

    operator fun invoke() =jokeRepository.getJokeList()


}