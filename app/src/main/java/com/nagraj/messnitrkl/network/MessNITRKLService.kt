package com.nagraj.messnitrkl.network

import com.nagraj.messnitrkl.network.register.request.RegisterRequest
import com.nagraj.messnitrkl.network.register.response.RegisterResponse
import com.nagraj.messnitrkl.network.updatechoice.request.UpdateChoiceRequest
import com.nagraj.messnitrkl.network.updatechoice.response.UpdateChoiceResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface MessNITRKLService {
    @Headers("Content-Type: application/json")
    @POST("/api/register_student/")
    fun registerStudent(@Body request: RegisterRequest?): Call<RegisterResponse?>?

    @Headers("Content-Type: application/json")
    @POST("/api/update_student_choice/")
    fun updateChoice(@Body request: UpdateChoiceRequest?): Call<UpdateChoiceResponse?>?
}