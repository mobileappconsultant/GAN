package com.android.breakingbad.domain.mapper

import com.android.breakingbad.domain.model.BreakbadCharacterRoomItem
import com.android.breakingbad.domain.model.BreakingBadDataItem

class RoomResponseToHospitalItemMapper : ModelMapper<BreakbadCharacterRoomItem, BreakingBadDataItem> {

    override fun mapFromModel(model: BreakbadCharacterRoomItem): BreakingBadDataItem {
        return BreakingBadDataItem(
            model.appearance!!, model.birthday!!,
            model.better_call_saul_appearance!!, model.category!!,
            model.char_id!!, model.img!!,
            model.name!!, model.nickname!!,
            model.occupation!!, model.portrayed!!, model.status!!,
        )
    }
}
