package com.example.anectodus.presentation.viewModels.states

import com.example.anectodus.domain.entity.SomeJoke

sealed class AddJokeState {
}

object Error : AddJokeState()
class AddJoke(val addJoke : Unit) : AddJokeState()
