package com.android.breakingbad.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class BreakingBadDataItem(
    @SerializedName("appearance")
    @Expose
    val appearance: List<Int> = emptyList(),
    @SerializedName("birthday")
    @Expose
    val birthday: String = "",
    @SerializedName("better_call_saul_appearance")
    @Expose
    val better_call_saul_appearance: List<Int> = emptyList(),

    @SerializedName("category")
    @Expose
    val category: String = "",
    @SerializedName("char_id")
    @Expose
    val char_id: Int = 0,
    @SerializedName("img")
    @Expose
    val img: String = "",
    @SerializedName("name")
    @Expose
    val name: String = "",
    @SerializedName("nickname")
    @Expose
    val nickname: String = "",
    @SerializedName("occupation")
    @Expose
    val occupation: List<String> = emptyList(),
    @SerializedName("portrayed")
    @Expose
    val portrayed: String = "",
    @SerializedName("status")
    @Expose
    val status: String = ""
)