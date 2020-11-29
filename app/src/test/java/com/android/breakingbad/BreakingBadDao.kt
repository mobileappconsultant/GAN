package com.android.breakingbad

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android.breakingbad.common.RoomRepository
import com.android.breakingbad.domain.mapper.BreakingBadResponseModelToRoomItemMapper
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class BreakingBadDao : DatabaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    var roomRepository: RoomRepository? = null

    @Before
    fun setup() {
        roomRepository =
            RoomRepository(appDatabase.breakingBadDao(), BreakingBadResponseModelToRoomItemMapper())
    }

    @Test
    fun insertSingleHospitalTest() {
        roomRepository?.insertActors(listOf(BreakingBadDataFactory.makeSingleBreakingBadActor()))
            ?.test()?.assertComplete()
    }

    @Test
    fun insertMultipleHospitalsTest() {
        val predefinedEntrySize = 10
        roomRepository?.insertActors(
            BreakingBadDataFactory.makeMultipleActorDataItems(
                predefinedEntrySize
            ).toList()
        )
            ?.test()?.assertComplete()

    }

    @Test
    fun deleteHospitalEntryTest() {
        val roomHospitalItem = BreakingBadDataFactory.makeSingleActor()
        appDatabase.breakingBadDao()
            .insertBreakingBadCharacterItems(mutableListOf(roomHospitalItem))

    }

}
