package com.example.anectodus.presentation.viewModels.states

import com.example.anectodus.domain.entity.SomeJoke

sealed class HomeState {

}
object Loading : HomeState()

class Content(val listJoke : List<SomeJoke>?) : HomeState()