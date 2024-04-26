package com.example.anectodus.data.remoteDataSource

import android.app.Application
import android.util.Log
import com.example.anectodus.data.db.JokeListDao
import com.example.anectodus.domain.entity.SomeJoke
import com.example.anectodus.domain.repository.JokeRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class JokeRepositoryImpl @Inject constructor(
    val application: Application,
    val dao : JokeListDao,
    val firestore: FirebaseFirestore,
    val firebaseAuth: FirebaseAuth
): JokeRepository {



    override suspend fun addJoke(someJoke: SomeJoke) {
        firestore.collection("jokes").add(someJoke).addOnSuccessListener { isCorrect ->
            Log.d("AddJoke", "DocumentSnapshot written with ID: ${isCorrect.id}")
        }.addOnFailureListener {
            throw RuntimeException("vashe pizda")
        }
    }

    override suspend fun deleteJoke(someJoke: SomeJoke) {
        dao.deleteJoke(someJoke.id)
    }


    override fun getJokeList(): Flow<List<SomeJoke>> = flow {
        val getJokes = firestore.collection("jokes").get()
        val jokeList = mutableListOf<SomeJoke>()
        getJokes.addOnSuccessListener {
            jokeList.addAll(it.toObjects<SomeJoke>())
            Log.d("AddJoke", "JokeRepositoryImpl addOnSuccessListener : ${it.size()}")

        }.addOnFailureListener{
            throw RuntimeException("JokeRepositoryImpl addOnFailureListener : ${it.message}")
        }

        while (jokeList.isEmpty()){
            delay(100)
        }
        emit(jokeList)
    }

    override fun getJokeListForAccountPost(): Flow<List<SomeJoke>> = flow {
        val getJokes = firestore.collection("jokes").get()
        val jokeList = mutableListOf<SomeJoke>()
        getJokes.addOnSuccessListener {result ->
            val joke = result.toObjects<SomeJoke>().filter { it.idFromUser == firebaseAuth.currentUser!!.uid  }
            jokeList.addAll(joke)
            Log.d("AddJoke", "JokeRepositoryImpl addOnSuccessListener : ${result.size()}")

        }.addOnFailureListener{
            throw RuntimeException("JokeRepositoryImpl addOnFailureListener : ${it.message}")
        }

        while (jokeList.isEmpty()){
            delay(100)
        }
        val list = jokeList.filter { it.idFromUser == firebaseAuth.currentUser!!.uid }
        emit(list)
    }


}