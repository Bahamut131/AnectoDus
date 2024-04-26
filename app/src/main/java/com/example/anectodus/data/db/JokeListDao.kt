package com.example.anectodus.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.anectodus.data.entity.SomeJokeDbModel

@Dao
interface JokeListDao  {

    @Query("SELECT * FROM SOME_JOKE")
    fun getJokeList() : LiveData<List<SomeJokeDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addJoke(someJokeDbModel: SomeJokeDbModel)

    @Query("DELETE FROM SOME_JOKE WHERE id=:id")
    suspend fun deleteJoke(id : Int)

    @Query("SELECT * FROM SOME_JOKE WHERE id=:id LIMIT 1")
    suspend fun getJoke(id : Int) : SomeJokeDbModel

}