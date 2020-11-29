package com.android.breakingbad.common

import com.android.breakingbad.domain.database.BreakingBadDao
import com.android.breakingbad.domain.database.TaskRepository
import com.android.breakingbad.domain.mapper.BreakingBadResponseModelToRoomItemMapper
import com.android.breakingbad.domain.model.BreakbadCharacterRoomItem
import com.android.breakingbad.domain.model.BreakingBadDataItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val breakingBadDao: BreakingBadDao,
    private val mapper: BreakingBadResponseModelToRoomItemMapper
) : TaskRepository {

    override fun insertActors(actors: List<BreakingBadDataItem>): Completable {

        return breakingBadDao.insertBreakingBadCharacterItems(actors.map {
            mapper.mapFromModel(it)
        }
            .toMutableList())
    }

    override fun getAllActors(): Observable<List<BreakbadCharacterRoomItem>> {
        return breakingBadDao.retrieveAllActors()
    }

    override fun deleteAllActors(): Completable {
        return breakingBadDao.deleteAll()
    }

    override fun queryActorsBySeason(season: Int): Observable<List<BreakbadCharacterRoomItem>> {
        var bla = emptyList<BreakbadCharacterRoomItem>()
        return getAllActors()
            .map {
                it.filter { !it.appearance.isNullOrEmpty() }
                    .filter { it.appearance?.contains(season)!! }
            }


    }

    override fun queryActorsByName(actorName: String): Observable<List<BreakbadCharacterRoomItem>> {
        return breakingBadDao.queryActorByName(actorName)
    }

}