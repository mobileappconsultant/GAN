package com.android.breakingbad.domain.repository

import com.android.breakingbad.common.TaskResult
import com.android.breakingbad.domain.model.BreakbadCharacterRoomItem
import com.android.breakingbad.domain.model.BreakingBadDataItem
import com.android.breakingbad.domain.model.BreakingBadPayLoad
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.Response

interface Repository {
     fun retrieveAllActors(): Single<TaskResult<Boolean>>
     fun retrieveActorsBySeason(season: Int): Observable<List<BreakbadCharacterRoomItem>>
     fun deleteAllActors(): Completable
     fun retrieveAllActorsFromRemoteSource(): Single<Response<BreakingBadPayLoad>>
}
