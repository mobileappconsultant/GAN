package com.android.breakingbad.domain.database

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.android.breakingbad.domain.database.DBConstants.ROOMTABLECONSTANTS.FIELD_NAME
import com.android.breakingbad.domain.model.BreakbadCharacterRoomItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface BreakingBadDao {

    @RawQuery
    fun retrieveAllActors(query: SupportSQLiteQuery): Single<List<BreakbadCharacterRoomItem>>

    @Query("SELECT * FROM ${DBConstants.ROOMTABLECONSTANTS.TABLE_NAME} WHERE ${FIELD_NAME} LIKE :nameTitle")
    fun queryActorByName(nameTitle: String): io.reactivex.rxjava3.core.Observable<List<BreakbadCharacterRoomItem>>

    @Query(DBConstants.ROOMTABLECONSTANTS.DEFAULT_QUERY_STRING)
    fun retrieveAllActors(): io.reactivex.rxjava3.core.Observable<List<BreakbadCharacterRoomItem>>


    @Query("DELETE FROM ${DBConstants.ROOMTABLECONSTANTS.TABLE_NAME}")
    fun deleteAll(): Completable


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertBreakingBadCharacterItems(list: MutableList<BreakbadCharacterRoomItem>): Completable
}
