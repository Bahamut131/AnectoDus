package com.example.anectodus.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anectodus.domain.useCase.authUseCase.SingInUseCase
import com.example.anectodus.presentation.viewModels.states.AuthResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(
    private val singInUseCase: SingInUseCase
) : ViewModel()
{

    suspend fun sendRequest(email : String,password: String,userName : String) : AuthResult{
       return singInUseCase.invoke(email = email, password = password, userName = userName)
    }

    private val _authState = MutableLiveData<AuthResult>()

    val authState: LiveData<AuthResult> get() = _authState

    fun sendCredentials(email: String, password: String,userName : String) {
        viewModelScope.launch(Dispatchers.IO) {
            _authState.postValue(AuthResult.Loading)
            val result = sendRequest(email, password,userName)
            _authState.postValue(result)
        }
    }
}