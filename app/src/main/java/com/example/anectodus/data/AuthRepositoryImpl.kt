package com.example.anectodus.data

import android.app.Application
import com.example.anectodus.domain.entity.User
import com.example.anectodus.domain.repository.AuthRepository
import com.example.anectodus.presentation.viewModels.states.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db : FirebaseDatabase,
    val application: Application
) : AuthRepository {

    private var _refreshEmail : String ?= null
    private var _refreshPassword: String ?= null


    override suspend fun signInWithEmailAndPassword(email: String, password: String, userName : String): AuthResult {
        return try {
            val user = auth.signInWithEmailAndPassword(email, password).await().user!!
            AuthResult.Success(User.Base(user.email ?: " ", user.uid, userName))
        } catch (e: Exception) {
            AuthResult.Error(e)
        }
    }

    override suspend fun signUpWithEmailAndPassword(email: String, password: String,userName : String): AuthResult {
        return try {
            val user = auth.createUserWithEmailAndPassword(email, password).await().user!!
            AuthResult.Success(User.Base(user.email ?: " ", user.uid, userName))
        } catch (e: Exception) {
            AuthResult.Error(e)
        }
    }

    override suspend fun addUserToDB(email: String, userName: String) {
        val userInfo = mutableMapOf<String,String>()
        userInfo.put("email",email)
        userInfo.put("username",userName)
        db.reference.child("Users").child(auth.currentUser!!.uid).setValue(userInfo)
    }
}