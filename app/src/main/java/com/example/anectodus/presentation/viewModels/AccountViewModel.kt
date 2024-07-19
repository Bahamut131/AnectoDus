package com.example.anectodus.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anectodus.domain.entity.SomeJoke
import com.example.anectodus.domain.useCase.jokeUseCase.GetJokeListForAccountLikeUseCase
import com.example.anectodus.domain.useCase.jokeUseCase.GetJokeListForAccountPostUseCase
import com.example.anectodus.presentation.viewModels.states.Content
import com.example.anectodus.presentation.viewModels.states.HomeState
import com.example.anectodus.presentation.viewModels.states.Loading
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountViewModel  @Inject constructor(
    val getJokeListForAccountPostUseCase: GetJokeListForAccountPostUseCase,
    val getJokeListForAccountLikeUseCase: GetJokeListForAccountLikeUseCase,
    val firebaseDatabase: FirebaseDatabase,
    val firebaseAuth: FirebaseAuth
): ViewModel() {

    private var _userName = MutableLiveData<String>()
    val userName : LiveData<String> get() =  _userName

    init {
        takeUserName()
    }
    val jokePostList : Flow<HomeState> = getJokeListForAccountPostUseCase()
    .filter{it.isNotEmpty() }
    .map { Content(listJoke = it) as HomeState }
    .onStart { emit(Loading) }


    val jokeLikeList : Flow<HomeState> = getJokeListForAccountLikeUseCase()
        .filter{it.isNotEmpty() }
        .map { Content(listJoke = it) as HomeState }
        .onStart { emit(Loading) }


    fun deleteJoke(someJoke: SomeJoke){
        viewModelScope.launch {
            //deleteAccountJokeUseCase.invoke(someJoke)
        }
        val mutableList : MutableList<Int> = mutableListOf()
    }

    private fun takeUserName() {
        viewModelScope.launch {
            firebaseDatabase.reference.child("Users").child(firebaseAuth.currentUser!!.uid).addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    _userName.value = snapshot.child("username").getValue().toString()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }

    }
}