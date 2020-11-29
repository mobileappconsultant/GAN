package com.android.breakingbad.domain.repository

import com.android.breakingbad.common.RoomRepository
import com.android.breakingbad.common.TaskResult
import com.android.breakingbad.domain.model.BreakbadCharacterRoomItem
import com.android.breakingbad.domain.model.BreakingBadDataItem
import com.android.breakingbad.domain.model.BreakingBadPayLoad
import com.android.breakingbad.domain.remote.BreakingBadApiQueryService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import javax.inject.Inject

class BreakingItemsQueryRepository @Inject constructor(
    private val breakingBadApiQueryService: BreakingBadApiQueryService,
    private val taskRepository: RoomRepository
) :
    Repository {

    var hashSetSeasons: HashSet<Int> = hashSetOf()

    init {
        deleteAllActors().subscribe()
    }

    override fun retrieveAllActorsFromRemoteSource(): Single<Response<BreakingBadPayLoad>> {
        return breakingBadApiQueryService.retrieveBreakingBadCharacters()
    }

    override fun retrieveAllActors(): Single<TaskResult<Boolean>> {
        return breakingBadApiQueryService.retrieveBreakingBadCharacters().doOnSuccess {
            getSeasons(it.body()?.list!!)
            TaskResult.Success(true)
        }.doOnError { TaskResult.Error(Exception("There was an error")) }
            .flatMapCompletable {
                taskRepository.insertActors(it.body()?.list!!)
            }
            .toSingleDefault(TaskResult.Success(true))

    }

    fun getSeasons(list: MutableList<BreakingBadDataItem>) {
        list.filter { !it.appearance.isNullOrEmpty() }.map {
            hashSetSeasons.addAll(
                it.appearance!!
            )
        }

    }

    override fun retrieveActorsBySeason(season: Int): Observable<List<BreakbadCharacterRoomItem>> {
        return taskRepository.queryActorsBySeason(season)
    }

    override fun deleteAllActors(): Completable {
        return taskRepository.deleteAllActors()
    }
}
