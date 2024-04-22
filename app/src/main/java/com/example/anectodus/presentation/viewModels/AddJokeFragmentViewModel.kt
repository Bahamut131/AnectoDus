package com.example.anectodus.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anectodus.domain.entity.SomeJoke
import com.example.anectodus.domain.useCase.AddJokeUseCase
import com.example.anectodus.presentation.viewModels.states.AddJoke
import com.example.anectodus.presentation.viewModels.states.AddJokeState
import com.example.anectodus.presentation.viewModels.states.Error
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddJokeFragmentViewModel @Inject constructor(
    val addJokeUseCase: AddJokeUseCase
) : ViewModel() {

    private val _state = MutableLiveData<AddJokeState>()
    val state : LiveData<AddJokeState> get() = _state


    fun addJoke(someJokeText: String){
        if(someJokeText.isBlank()){
            _state.value = Error
            return
        }
        viewModelScope.launch {
            _state.value = AddJoke(addJokeUseCase(SomeJoke(text = someJokeText)))
        }
    }
}