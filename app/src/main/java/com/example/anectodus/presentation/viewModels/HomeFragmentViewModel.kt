package com.example.anectodus.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anectodus.domain.entity.SomeJoke
import com.example.anectodus.domain.useCase.jokeUseCase.DeleteJokeUseCase
import com.example.anectodus.domain.useCase.jokeUseCase.GetListJokUseCase
import com.example.anectodus.presentation.viewModels.states.Content
import com.example.anectodus.presentation.viewModels.states.HomeState
import com.example.anectodus.presentation.viewModels.states.Loading
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragmentViewModel @Inject constructor(
    val getListJokUseCase: GetListJokUseCase,
    val deleteJokeUseCase: DeleteJokeUseCase,
) : ViewModel() {

    val jokeList : Flow<HomeState> = getListJokUseCase.invoke()
        .filter{it.isNotEmpty() }
        .map { Content(listJoke = it) as HomeState}
        .onStart { emit(Loading) }



    fun deleteJoke(someJoke: SomeJoke){
        viewModelScope.launch {
            deleteJokeUseCase.invoke(someJoke)

        }
    }
}