package com.example.anectodus.di

import androidx.lifecycle.ViewModel
import com.example.anectodus.presentation.viewModels.AccountViewModel
import com.example.anectodus.presentation.viewModels.AddJokeFragmentViewModel
import com.example.anectodus.presentation.viewModels.AuthorizationViewModel
import com.example.anectodus.presentation.viewModels.HomeFragmentViewModel
import com.example.anectodus.presentation.viewModels.RegistrationViewModel
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

    @Binds
    @ViewModelKey(RegistrationViewModel::class)
    @IntoMap
    fun registrationViewModel(viewModel: RegistrationViewModel) : ViewModel

    @Binds
    @ViewModelKey(AuthorizationViewModel::class)
    @IntoMap
    fun authorizationViewModel(viewModel: AuthorizationViewModel) : ViewModel

    @Binds
    @ViewModelKey(AccountViewModel::class)
    @IntoMap
    fun accountViewModel(viewModel: AccountViewModel) : ViewModel

}