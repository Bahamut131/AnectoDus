package com.example.anectodus.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.anectodus.domain.entity.SomeJoke

@Database(entities = [SomeJokeDbModel::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun jokeListDao() : JokeListDao

    companion object{

        const val NAME_DB = "JOKE_DB"
        private val LOCK = Any()
        private var INSTANCE  : AppDataBase ?= null

        fun newInstance(application: Application) : AppDataBase{

            INSTANCE?.let { return it }

            synchronized(LOCK){
                INSTANCE?.let { return it }

                val db = Room.databaseBuilder(
                    application,
                    AppDataBase::class.java,
                    NAME_DB).fallbackToDestructiveMigration().build()

                INSTANCE = db

                return db
            }
        }

    }

}