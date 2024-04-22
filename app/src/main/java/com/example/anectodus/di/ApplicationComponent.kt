package com.example.anectodus.di

import android.app.Application
import com.example.anectodus.presentation.fragments.AddJokeFragment
import com.example.anectodus.presentation.fragments.HomeFragment
import com.example.anectodus.presentation.activity.MainActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class,ViewModelModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: AddJokeFragment)


    @Component.Factory
    interface Factory{

        fun create(
            @BindsInstance application: Application
        ) : ApplicationComponent

    }


}