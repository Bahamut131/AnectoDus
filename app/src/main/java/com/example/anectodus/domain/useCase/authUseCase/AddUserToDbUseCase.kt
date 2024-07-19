package com.example.anectodus.domain.useCase.authUseCase

import com.example.anectodus.domain.repository.AuthRepository
import javax.inject.Inject

class AddUserToDbUseCase @Inject constructor(val repository: AuthRepository) {

    suspend operator fun invoke(email: String,userName : String,userLikes : List<String>?){
        repository.addUserToDB(email, userName,userLikes)
    }

}