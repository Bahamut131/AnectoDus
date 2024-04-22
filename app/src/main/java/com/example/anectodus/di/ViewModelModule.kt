package com.example.anectodus.di

import androidx.lifecycle.ViewModel
import com.example.anectodus.presentation.viewModels.AddJokeFragmentViewModel
import com.example.anectodus.presentation.viewModels.HomeFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
interface ViewModelModule {

    @Binds
    @ViewModelKey(HomeFragmentViewModel::class)
    @IntoMap
    fun mainViewModel(viewModel: HomeFragmentViewModel) : ViewModel

    @Binds
    @ViewModelKey(AddJokeFragmentViewModel::class)
    @IntoMap
    fun addJokeViewModel(viewModel: AddJokeFragmentViewModel) : ViewModel

}