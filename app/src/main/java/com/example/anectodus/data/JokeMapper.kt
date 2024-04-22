package com.example.anectodus.data

import com.example.anectodus.domain.entity.SomeJoke
import javax.inject.Inject

class JokeMapper @Inject constructor(){

    fun mapDbModelToEntity(someJokeDbModel: SomeJokeDbModel) : SomeJoke{
        return SomeJoke(
            id = someJokeDbModel.id,
            text = someJokeDbModel.text,
            likeJoke = someJokeDbModel.likeJoke
        )
    }

    fun mapEntityToDbModel(someJoke: SomeJoke) : SomeJokeDbModel{
        return SomeJokeDbModel(
            id = someJoke.id,
            text = someJoke.text,
            likeJoke = someJoke.likeJoke
        )
    }

    fun mapListDBToEntityList(list : List<SomeJokeDbModel>) = list.map {
        mapDbModelToEntity(it)
    }


}