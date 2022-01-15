package com.nagraj.messnitrkl.network.getstudentchoice.request

import com.google.gson.annotations.SerializedName

data class GetStudentChoiceRequest(
    @SerializedName("rollNo") val rollNo: String?,
)
