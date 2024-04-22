package com.example.anectodus.di

import android.app.Application
import com.example.anectodus.presentation.customView.CardLayoutManager
import com.example.anectodus.data.AppDataBase
import com.example.anectodus.data.JokeListDao
import com.example.anectodus.data.JokeRepositoryImpl
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
    fun bindCardLayoutManager(card : CardLayoutManager) : CardLayoutManager


    companion object{
        @Provides
        @ApplicationScope
        fun provideJokeListDao(application: Application) : JokeListDao{
            return AppDataBase.newInstance(application).jokeListDao()
        }
    }

}