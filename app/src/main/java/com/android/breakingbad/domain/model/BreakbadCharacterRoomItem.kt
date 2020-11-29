package com.android.breakingbad.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.breakingbad.domain.database.DBConstants
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = DBConstants.ROOMTABLECONSTANTS.TABLE_NAME)
class BreakbadCharacterRoomItem(

    @field:ColumnInfo(name = DBConstants.ROOMTABLECONSTANTS.FIELD_APPEARANCE)
    val appearance: List<Int>?,

    @field:ColumnInfo(name = DBConstants.ROOMTABLECONSTANTS.FIELD_Birthday)
    val birthday: String?,

    @field:ColumnInfo(name = DBConstants.ROOMTABLECONSTANTS.FIELD_BETTER_CALL_SAUL_APPEARANCE)
    val better_call_saul_appearance: List<Int>?,

    @field:ColumnInfo(name = DBConstants.ROOMTABLECONSTANTS.FIELD_CATEGORY)
    val category: String?,

    @field:ColumnInfo(name = DBConstants.ROOMTABLECONSTANTS.FIELD_CHAR_ID)
    val char_id: Int?,

    @field:ColumnInfo(name = DBConstants.ROOMTABLECONSTANTS.FIELD_IMAGE)
    val img: String?,
    @field:ColumnInfo(name = DBConstants.ROOMTABLECONSTANTS.FIELD_NAME)
    val name: String?,

    @field:ColumnInfo(name = DBConstants.ROOMTABLECONSTANTS.FIELD_NICKNAME)
    val nickname: String?,

    @field:ColumnInfo(name = DBConstants.ROOMTABLECONSTANTS.FIELD_OCCUPATION)
    val occupation: List<String>?,

    @field:ColumnInfo(name = DBConstants.ROOMTABLECONSTANTS.FIELD_PORTRAYED)
    val portrayed: String?,

    @field:ColumnInfo(name = DBConstants.ROOMTABLECONSTANTS.FIELD_STATUS)
    val status: String?,
    @PrimaryKey(autoGenerate = true) val id: Int,

    ) : Parcelable {
    var title: String = ""
    var titleHolder: String = ""
    fun setTitle(block: () -> String) {
        title = block()
    }
}


