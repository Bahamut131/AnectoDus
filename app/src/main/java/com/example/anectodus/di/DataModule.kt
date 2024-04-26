package com.example.anectodus.di

import android.app.Application
import com.example.anectodus.presentation.customView.CardLayoutManager
import com.example.anectodus.data.db.AppDataBase
import com.example.anectodus.data.remoteDataSource.AuthRepositoryImpl
import com.example.anectodus.data.db.JokeListDao
import com.example.anectodus.data.remoteDataSource.JokeRepositoryImpl
import com.example.anectodus.domain.repository.AuthRepository
import com.example.anectodus.domain.repository.JokeRepository
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindJokeRepository(impl : JokeRepositoryImpl) : JokeRepository


    @Binds
    @ApplicationScope
    fun bindAuthRepository(impl : AuthRepositoryImpl) : AuthRepository

    @Binds
    @ApplicationScope
    fun bindCardLayoutManager(card : CardLayoutManager) : CardLayoutManager


    companion object{
        @Provides
        @ApplicationScope
        fun provideJokeListDao(application: Application) : JokeListDao {
            return AppDataBase.newInstance(application).jokeListDao()
        }
    }

}