package com.android.breakingbad.domain.database

object DBConstants {
    object ROOMTABLECONSTANTS {
        const val FIELD_APPEARANCE = "appearance"
        const val FIELD_Birthday = "birthday"
        const val FIELD_BETTER_CALL_SAUL_APPEARANCE = "better_call_saul_appearance"
        const val FIELD_CATEGORY = "category"
        const val FIELD_CHAR_ID = "char_id"
        const val FIELD_IMAGE = "img"
        const val FIELD_NAME = "name"
        const val FIELD_NICKNAME = "nickname"
        const val FIELD_OCCUPATION = "occupation"
        const val FIELD_PORTRAYED = "portrayed"
        const val FIELD_STATUS = "status"
        const val TABLE_NAME = "BreakingBadCast"
        const val DATABASE_NAME = "BreakingBadCastDatabase"
        const val DEFAULT_QUERY_STRING = "SELECT * FROM ${TABLE_NAME}"
    }

}
