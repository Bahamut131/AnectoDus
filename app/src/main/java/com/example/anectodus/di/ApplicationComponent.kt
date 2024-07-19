package com.example.anectodus.di

import android.app.Application
import com.example.anectodus.presentation.fragments.AddJokeFragment
import com.example.anectodus.presentation.fragments.HomeFragment
import com.example.anectodus.presentation.activity.MainActivity
import com.example.anectodus.presentation.fragments.AccountFragment
import com.example.anectodus.presentation.fragments.AuthorizationFragment
import com.example.anectodus.presentation.fragments.LikeJokeFragment
import com.example.anectodus.presentation.fragments.RegisterFragment
import com.example.anectodus.presentation.fragments.UserePostJokeFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class,ViewModelModule::class,FireBaseModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: AddJokeFragment)
    fun inject(fragment: RegisterFragment)
    fun inject(fragment: AuthorizationFragment)
    fun inject(fragment: AccountFragment)
    fun inject(fragment: UserePostJokeFragment)
    fun inject(fragment: LikeJokeFragment)


    @Component.Factory
    interface Factory{

        fun create(
            @BindsInstance application: Application
        ) : ApplicationComponent

    }


}