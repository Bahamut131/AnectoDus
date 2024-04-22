package com.example.anectodus.presentation.viewModels.states

import com.example.anectodus.domain.entity.User

sealed class AuthResult {

    class Success(val user: User) : AuthResult()

    class Error(val e: Exception) : AuthResult()

    object Loading : AuthResult()
}

