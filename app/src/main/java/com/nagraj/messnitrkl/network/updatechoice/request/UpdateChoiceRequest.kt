package com.nagraj.messnitrkl.network.updatechoice.request

import com.google.gson.annotations.SerializedName

data class UpdateChoiceRequest(
    @SerializedName("rollNo") val rollNo: String?,
    @SerializedName("choiceCode") val choiceCode: String?,
    @SerializedName("recordTime") val recordTime: String?
)
