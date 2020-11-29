package com.android.breakingbad.domain.database

import com.android.breakingbad.domain.model.BreakbadCharacterRoomItem
import com.android.breakingbad.domain.model.BreakingBadDataItem
import com.android.breakingbad.domain.model.BreakingBadPayLoad
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface TaskRepository {
  fun getAllActors(): Observable<List<BreakbadCharacterRoomItem>>
  fun deleteAllActors():Completable
  fun queryActorsBySeason(season:Int): Observable<List<BreakbadCharacterRoomItem>>
  fun queryActorsByName(actorName:String): Observable<List<BreakbadCharacterRoomItem>>
  fun insertActors(actors: List<BreakingBadDataItem>): Completable
}