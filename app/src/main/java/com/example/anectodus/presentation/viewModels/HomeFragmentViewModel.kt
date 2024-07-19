package com.example.anectodus.presentation.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anectodus.domain.entity.SomeJoke
import com.example.anectodus.domain.useCase.jokeUseCase.DeleteJokeUseCase
import com.example.anectodus.domain.useCase.jokeUseCase.GetListJokUseCase
import com.example.anectodus.domain.useCase.jokeUseCase.LikeJokeUseCase
import com.example.anectodus.presentation.viewModels.states.Content
import com.example.anectodus.presentation.viewModels.states.HomeState
import com.example.anectodus.presentation.viewModels.states.Loading
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragmentViewModel @Inject constructor(
    val getListJokUseCase: GetListJokUseCase,
    val likeJokeUseCase: LikeJokeUseCase
) : ViewModel() {

    private val _jokeList = MutableStateFlow<HomeState>(Loading)
    val jokeList: StateFlow<HomeState> get() = _jokeList

    private val currentJokes = mutableListOf<SomeJoke>()

    fun loadJoke(lastVisibility: SomeJoke?) {
        viewModelScope.launch {
            _jokeList.emit(Loading)
            getListJokUseCase.invoke(lastVisibility)
                .filter { it.isNotEmpty() }
                .collect { newJokes ->
                    val filteredJokes = newJokes.filter { newJoke ->
                        currentJokes.none { it.id == newJoke.id }
                    }
                    currentJokes.addAll(filteredJokes)
                    _jokeList.emit(Content(listJoke = currentJokes.toList()))
                }
        }
    }


    suspend fun likeJoke(joke: SomeJoke) {
        likeJokeUseCase.invoke(joke)
    }

}