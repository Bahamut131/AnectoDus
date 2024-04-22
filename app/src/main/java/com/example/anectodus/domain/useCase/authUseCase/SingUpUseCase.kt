package com.example.anectodus.domain.useCase.authUseCase

import com.example.anectodus.domain.repository.AuthRepository
import com.example.anectodus.presentation.viewModels.states.AuthResult
import javax.inject.Inject

class SingUpUseCase @Inject constructor(val repository: AuthRepository) {

    suspend operator fun invoke(email: String, password : String,userName : String) : AuthResult{
       return repository.signUpWithEmailAndPassword(email,password,userName)
    }
}