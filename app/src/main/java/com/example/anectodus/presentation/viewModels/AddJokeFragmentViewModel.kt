package com.example.anectodus.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anectodus.data.remoteDataSource.JokeRepositoryImpl
import com.example.anectodus.domain.entity.SomeJoke
import com.example.anectodus.domain.useCase.jokeUseCase.AddJokeUseCase
import com.example.anectodus.presentation.viewModels.states.AddJoke
import com.example.anectodus.presentation.viewModels.states.AddJokeState
import com.example.anectodus.presentation.viewModels.states.Error
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddJokeFragmentViewModel @Inject constructor(
    val addJokeUseCase: AddJokeUseCase,
    val firebaseAuth: FirebaseAuth,
    val firestore: FirebaseFirestore
) : ViewModel() {

    private val _state = MutableLiveData<AddJokeState>()
    val state: LiveData<AddJokeState> get() = _state


    fun addJoke(someJokeText: String?) {
        if (someJokeText.isNullOrBlank()) {
            _state.value = Error
            return
        }
        viewModelScope.launch {
            _state.value = AddJoke(
                addJokeUseCase(
                    someJokeText
                )
            )
        }
    }
}