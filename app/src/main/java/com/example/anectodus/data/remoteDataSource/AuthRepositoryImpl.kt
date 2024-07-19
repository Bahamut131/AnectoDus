package com.example.anectodus.data.remoteDataSource

import android.app.Application
import com.example.anectodus.domain.entity.User
import com.example.anectodus.domain.repository.AuthRepository
import com.example.anectodus.presentation.viewModels.states.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.values
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db : FirebaseDatabase,
    val application: Application
) : AuthRepository {


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

    override suspend fun addUserToDB(email: String, userName: String, userLikes : List<String>?) {
        val userInfo = mutableMapOf<String,String>()
        userInfo.put(EMAIL,email)
        userInfo.put(USER_NAME,userName)
        db.reference.child(USER_TABLE).child(auth.currentUser!!.uid).setValue(userInfo)
    }


    companion object{
        const val USER_TABLE = "Users"
        const val EMAIL = "email"
        const val USER_NAME = "username"
    }
}