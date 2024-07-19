package com.example.anectodus.domain.useCase.jokeUseCase

import com.example.anectodus.domain.entity.SomeJoke
import com.example.anectodus.domain.repository.JokeRepository
import javax.inject.Inject

class GetListJokUseCase @Inject constructor(private val jokeRepository: JokeRepository) {

    operator fun invoke(lastVisibility : SomeJoke?) =jokeRepository.getJokeList(lastVisibility)


}