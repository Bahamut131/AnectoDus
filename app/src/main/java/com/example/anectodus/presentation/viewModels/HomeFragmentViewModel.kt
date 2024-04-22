package com.example.anectodus.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anectodus.domain.entity.SomeJoke
import com.example.anectodus.domain.useCase.AddJokeUseCase
import com.example.anectodus.domain.useCase.GetListJokUseCase
import com.example.anectodus.domain.useCase.DeleteJokeUseCase
import com.example.anectodus.domain.useCase.GetJokeUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragmentViewModel @Inject constructor(
    val getListJokUseCase: GetListJokUseCase,
    val deleteJokeUseCase: DeleteJokeUseCase,
) : ViewModel() {

    val jokeList = getListJokUseCase()

    fun deleteJoke(someJoke: SomeJoke){
        viewModelScope.launch {
            deleteJokeUseCase.invoke(someJoke)

        }
    }
}