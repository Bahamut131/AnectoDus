package com.example.anectodus.data.remoteDataSource

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.anectodus.domain.entity.SomeJoke
import com.example.anectodus.domain.repository.JokeRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.core.OrderBy
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.time.Instant
import java.util.Date
import javax.inject.Inject

class JokeRepositoryImpl @Inject constructor(
    val application: Application,
    val firestore: FirebaseFirestore,
    val firebaseAuth: FirebaseAuth
) : JokeRepository {

    override suspend fun likeJoke(joke: SomeJoke) {
        firestore.collection(JOKES_TABLE).document(joke.id)
            .update(LIST_USER_LIKE, FieldValue.arrayUnion(firebaseAuth.currentUser!!.uid))
            .addOnSuccessListener { isCorrect ->
                Log.d("onSwiped", "Added new user in list joke")
            }.addOnFailureListener {
                throw RuntimeException("vashe pizda")
            }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun addJoke(jokeText: String) {
        val date = Date.from(Instant.now())
        val joke = SomeJoke(
            id = firestore.collection("jokes").document().id,
            text = jokeText,
            idFromUser = firebaseAuth.currentUser!!.uid,
            timestamp = Timestamp(date)
        )

        firestore.collection(JOKES_TABLE).document(joke.id).set(joke)
            .addOnSuccessListener { isCorrect ->
                Log.d("onSwiped", "DocumentSnapshot written with ID: ${isCorrect}")
            }.addOnFailureListener {
                throw RuntimeException("vashe pizda")
            }
    }

    override suspend fun deleteJoke(someJoke: SomeJoke) {
        //TODO
    }


    override fun getJokeList(lastVisibility: SomeJoke?): Flow<List<SomeJoke>> = flow {
        val query: Task<QuerySnapshot> = if (lastVisibility == null) {
            firestore.collection(JOKES_TABLE).orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(5).get()
        } else {
            firestore.collection(JOKES_TABLE)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .startAfter(lastVisibility.timestamp)
                .limit(5).get()
        }

        val result = query.await() // Используем await() для ожидания результата асинхронно
        val newList = result.toObjects<SomeJoke>()
        emit(newList)
    }

    override fun getJokeListForAccountPost(): Flow<List<SomeJoke>> = flow {
        val getJokes = firestore.collection(JOKES_TABLE).get()
        val jokeList = mutableListOf<SomeJoke>()
        getJokes.addOnSuccessListener { result ->
            val joke = result.toObjects<SomeJoke>()
                .filter { it.idFromUser == firebaseAuth.currentUser!!.uid }
            jokeList.addAll(joke)
            Log.d("AddJoke", "JokeRepositoryImpl addOnSuccessListener : ${result.size()}")

        }.addOnFailureListener {
            throw RuntimeException("JokeRepositoryImpl addOnFailureListener : ${it.message}")
        }

        getJokes.await()
        emit(jokeList)
    }

    override fun getJokeListForAccountLike(): Flow<List<SomeJoke>> = flow {
        val jokeList = mutableListOf<SomeJoke>()
        firestore.collection(JOKES_TABLE).get().addOnSuccessListener { documents ->
            for (document in documents) {
                document.toObject<SomeJoke>().userLikePost?.map {
                    if (it == firebaseAuth.currentUser?.uid) {
                        jokeList.add(document.toObject<SomeJoke>())
                    }
                }
                Log.d("JokeRepositoryImpl", "${document.id} => ${document.data}")
            }
        }.addOnFailureListener { exception ->
            Log.w("JokeRepositoryImpl", "Error getting documents: ", exception)
        }

        while (jokeList.isEmpty()) {
            delay(100)
        }
        emit(jokeList)
    }

    companion object {
        const val JOKES_TABLE = "jokes"
        const val LIST_USER_LIKE = "userLikePost"
    }
}