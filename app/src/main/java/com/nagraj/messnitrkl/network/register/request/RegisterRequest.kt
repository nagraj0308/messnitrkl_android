package com.nagraj.messnitrkl.network.register.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("rollNo") val rollNo: String?,
    @SerializedName("hostel") val hostel: String?
)
