package com.android.breakingbad

import com.android.breakingbad.domain.model.BreakbadCharacterRoomItem
import com.android.breakingbad.domain.model.BreakingBadDataItem

object BreakingBadDataFactory {

    fun randomUuid(): String {
        return java.util.UUID.randomUUID().toString()
    }

    fun randomInt(): Int {
        return System.currentTimeMillis().toInt()
    }

    fun makeSingleActor(): BreakbadCharacterRoomItem {
        return BreakbadCharacterRoomItem(
            listOf(randomInt()),
            randomUuid(),
            listOf(randomInt()),
            randomUuid(),
            randomInt(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            listOf(randomUuid()),
            randomUuid(),
            randomUuid(),
            randomInt()
        )
    }

    fun makeSingleBreakingBadActor(): BreakingBadDataItem {
        return BreakingBadDataItem(
            listOf(randomInt()),
            randomUuid(),
            listOf(randomInt()),
            randomUuid(),
            'x'.toInt(),
            randomUuid(),
            randomUuid(),
            randomUuid(),
            listOf(randomUuid()),
            randomUuid(),
            randomUuid()
        )
    }

    fun makeMultipleActors(count: Int): MutableList<BreakbadCharacterRoomItem> {
        val listOfentries
        = mutableListOf<BreakbadCharacterRoomItem>()
        repeat(count) {
            listOfentries.add(makeSingleActor())
        }
        return listOfentries
    }


    fun makeMultipleActorDataItems(count: Int): MutableList<BreakingBadDataItem> {
        val listOfentries
                = mutableListOf<BreakingBadDataItem>()
        repeat(count) {
            listOfentries.add(makeSingleBreakingBadActor())
        }
        return listOfentries
    }




}
