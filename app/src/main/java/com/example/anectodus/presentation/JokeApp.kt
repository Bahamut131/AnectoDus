package com.example.anectodus.presentation

import android.app.Application
import com.example.anectodus.di.ApplicationComponent
import com.example.anectodus.di.DaggerApplicationComponent


class JokeApp : Application() {

    val component : ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

}