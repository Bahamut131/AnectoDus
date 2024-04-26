package com.example.anectodus.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anectodus.domain.useCase.authUseCase.SingUpUseCase
import com.example.anectodus.domain.useCase.authUseCase.AddUserToDbUseCase
import com.example.anectodus.presentation.viewModels.states.AuthResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val singUpUseCase: SingUpUseCase,
    private val addUserToDbUseCase: AddUserToDbUseCase,
) : ViewModel()
{

    suspend fun sendRequest(email : String,password: String,userName : String) : AuthResult {
        return singUpUseCase.invoke(email = email, password = password,userName = userName)
    }

    suspend fun createUserDbTable(email: String,userName: String)  {
        addUserToDbUseCase.invoke(email = email,userName = userName)
    }

    private val _authState = MutableLiveData<AuthResult>()

    val authState: LiveData<AuthResult> get() = _authState

    fun sendCredentials(email: String, password: String, userName : String) {
        viewModelScope.launch(Dispatchers.IO) {
            _authState.postValue(AuthResult.Loading)
            val result = sendRequest(email, password, userName)
            createUserDbTable(email,userName)
            _authState.postValue(result)
        }
    }

}