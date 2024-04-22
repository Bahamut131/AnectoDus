package com.example.anectodus.domain.repository

import com.example.anectodus.presentation.viewModels.states.AuthResult


interface AuthRepository {

    suspend fun signInWithEmailAndPassword(email: String, password: String, userName : String): AuthResult

    suspend fun signUpWithEmailAndPassword(email: String, password: String,userName : String): AuthResult

    suspend fun addUserToDB(email: String, userName: String)

}