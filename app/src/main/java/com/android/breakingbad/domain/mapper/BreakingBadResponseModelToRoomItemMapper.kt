package com.android.breakingbad.domain.mapper

import android.util.Log
import com.android.breakingbad.domain.model.BreakbadCharacterRoomItem
import com.android.breakingbad.domain.model.BreakingBadDataItem

class BreakingBadResponseModelToRoomItemMapper :
    ModelMapper<BreakingBadDataItem, BreakbadCharacterRoomItem> {
    override fun mapFromModel(model: BreakingBadDataItem): BreakbadCharacterRoomItem {
        return BreakbadCharacterRoomItem(
            model.appearance, model.birthday,
            model.better_call_saul_appearance, model.category,
            model.char_id, model.img,
            model.name, model.nickname,
            model.occupation, model.portrayed, model.status,0

        )
    }
}
