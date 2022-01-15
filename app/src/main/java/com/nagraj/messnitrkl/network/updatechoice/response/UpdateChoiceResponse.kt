package com.nagraj.messnitrkl.network.updatechoice.response

import com.nagraj.messnitrkl.network.getstudentchoice.response.Data


data class UpdateChoiceResponse(
    val `data`: Data,
    val msg: String,
    val isTrue: Int
)