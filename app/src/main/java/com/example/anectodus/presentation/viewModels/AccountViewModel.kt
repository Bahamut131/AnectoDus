package com.example.anectodus.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anectodus.domain.entity.SomeJoke
import com.example.anectodus.domain.useCase.jokeUseCase.DeleteAccountJokeUseCase
import com.example.anectodus.domain.useCase.jokeUseCase.GetAccountListJokUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountViewModel  @Inject constructor(
    val getAccountListJokUseCase: GetAccountListJokUseCase,
    val deleteAccountJokeUseCase: DeleteAccountJokeUseCase,
    val firebaseDatabase: FirebaseDatabase,
    val firebaseAuth: FirebaseAuth
): ViewModel() {

    private var _userName = MutableLiveData<String>()
    val userName : LiveData<String> get() =  _userName

    init {
        takeUserName()
    }

    val jokeList = getAccountListJokUseCase()

    fun deleteJoke(someJoke: SomeJoke){
        viewModelScope.launch {
            deleteAccountJokeUseCase.invoke(someJoke)

        }
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