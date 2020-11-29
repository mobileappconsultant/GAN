package com.android.breakingbad

import com.android.breakingbad.domain.mapper.BreakingBadResponseModelToRoomItemMapper
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BreakingBadResponseModelMapperTest {

    private val mapper = BreakingBadResponseModelToRoomItemMapper()

    @Test
    fun mapFromModelMapsData() {
        val model = BreakingBadDataFactory.makeSingleBreakingBadActor()
        val entity = mapper.mapFromModel(model)
        assertEquals(model.appearance.size, entity.appearance?.size)
        assertEquals(model.better_call_saul_appearance.size, entity.better_call_saul_appearance?.size)
        assertEquals(model.birthday, entity.birthday)
        assertEquals(model.category, entity.category)
        assertEquals(model.char_id, entity.char_id)
        assertEquals(model.img, entity.img)
        assertEquals(model.name, entity.name)
        assertEquals(model.nickname, entity.nickname)
        assertEquals(model.occupation, entity.occupation)
        assertEquals(model.portrayed, entity.portrayed)
        assertEquals(model.status, entity.status)
    }
}
