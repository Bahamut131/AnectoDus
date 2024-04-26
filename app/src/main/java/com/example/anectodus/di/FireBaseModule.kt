package com.example.anectodus.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module
interface FireBaseModule {

    companion object{
        @Provides
        @ApplicationScope
        fun provideFirebaseAuth(): FirebaseAuth {
            return Firebase.auth
        }

        @Provides
        @ApplicationScope
        fun provideFirebaseDB(): FirebaseDatabase {
            return Firebase.database
        }

        @Provides
        @ApplicationScope
        fun provideFirebaseCloud(): FirebaseFirestore {
            return Firebase.firestore
        }

    }

}