package com.example.anectodus.domain.useCase.authUseCase

import android.provider.ContactsContract.CommonDataKinds.Email
import com.example.anectodus.domain.entity.SomeJoke
import com.example.anectodus.domain.repository.AuthRepository
import com.example.anectodus.presentation.viewModels.states.AuthResult
import javax.inject.Inject

class SingInUseCase @Inject constructor(val repository: AuthRepository) {

    suspend operator fun invoke(email: String, password : String, userName : String) : AuthResult{
        return repository.signInWithEmailAndPassword(email,password,userName)
    }
}