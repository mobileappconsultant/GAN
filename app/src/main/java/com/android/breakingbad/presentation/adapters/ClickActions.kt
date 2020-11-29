package com.android.breakingbad.presentation.adapters

import com.android.breakingbad.domain.model.BreakbadCharacterRoomItem

sealed class ClickActions {

   class CheckboxClickAction(val checkBoxPosition: Int) : ClickActions()

   class MainViewClickAction(val breakbadCharacterRoomItem: BreakbadCharacterRoomItem) :
      ClickActions()

}
