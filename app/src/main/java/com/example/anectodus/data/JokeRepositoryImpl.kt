package com.example.anectodus.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.anectodus.domain.entity.SomeJoke
import com.example.anectodus.domain.repository.JokeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class JokeRepositoryImpl @Inject constructor(
    val application: Application,
    val mapper: JokeMapper,
    val dao : JokeListDao

): JokeRepository {


    val scope = CoroutineScope(Dispatchers.Default)
    init {
        scope.launch {
            addJoke(SomeJoke(0,"В долгих, трудных и очень-очень утомляющих поездках поездом или автобусом из одного конца света в другой; в длинных очередях, где скапливаются, казалось бы, жители всего города, двигающихся так медленно, что порой уловить это движение не удаётся вовсе, и где так трудно сохранить радостное настроение на продолжительный срок, ибо почти каждый представляет собой фонтан злости, усталости и раздражения; в томительном ожидании, когда подгоняемые опасениями опоздать вы оказываетесь на месте слишком рано и совершенно не представляете, как провести время до начала действа; во время лёгких и приятных прогулок с друзьями, когда уже все новости переданы, все события известны, все мысли озвучены и все идеи предложены, а темы для беседы подходят к концу; и ночью у уютного костра, где не хочется думать ни о чём, а только слушать забавные истории, веселиться и наслаждаться жизнью — везде будет к месту рассказать какой-нибудь смешной анекдот, причём не обыкновенный анекдот-коротышку, заканчивающийся тогда, когда в воздухе ещё звучит первое его слово, а длинный, обстоятельный анекдот, который не спеша льётся, медленно и лениво развивается, раскручивается, словно огромный удав, подогревая интерес слушателей, всё не прекращается, и также степенно выходит на финишную прямую, после чего неожиданно быстро, подобно молнии, сверкнувшей среди громадных серых туч, несколько часов собиравшихся на небе, обрушивает на собравшихся концовку, оглушает финальным аккордом, который, в свою очередь, утопает в дружном смехе, пришедшем на смену звонкой тишине ожидания, нарушаемой лишь монологом рассказчика.",true))


        }
    }

    override suspend fun addJoke(someJoke: SomeJoke) {
        dao.addJoke(mapper.mapEntityToDbModel(someJoke))
    }

    override suspend fun deleteJoke(someJoke: SomeJoke) {
        dao.deleteJoke(someJoke.id)
    }

    override suspend fun getJoke(someJoke: SomeJoke): SomeJoke {
        return mapper.mapDbModelToEntity(dao.getJoke(someJoke.id))
    }


    override fun getJokeList(): LiveData<List<SomeJoke>> {
        return dao.getJokeList().map {
            mapper.mapListDBToEntityList(it)
        }
    }

}