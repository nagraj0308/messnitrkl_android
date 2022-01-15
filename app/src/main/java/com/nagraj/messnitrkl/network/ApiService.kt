package com.nagraj.messnitrkl.network

import com.nagraj.messnitrkl.network.register.request.RegisterRequest
import com.nagraj.messnitrkl.network.register.response.RegisterResponse
import com.nagraj.messnitrkl.network.updatechoice.request.UpdateChoiceRequest
import com.nagraj.messnitrkl.network.updatechoice.response.UpdateChoiceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiService {

    fun registerStudent(registerRequest: RegisterRequest, onResult: (RegisterResponse?) -> Unit) {
        val retrofitBase = RetrofitBase.buildService(MessNITRKLService::class.java)
        retrofitBase.registerStudent(registerRequest)
            ?.enqueue(object : Callback<RegisterResponse?> {
                override fun onResponse(
                    call: Call<RegisterResponse?>,
                    response: Response<RegisterResponse?>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<RegisterResponse?>, t: Throwable) {
                    onResult(null)
                }
            })
    }

    fun updateChoice(updateChoiceRequest: UpdateChoiceRequest, onResult: (UpdateChoiceResponse?) -> Unit) {
        val retrofitBase = RetrofitBase.buildService(MessNITRKLService::class.java)
        retrofitBase.updateChoice(updateChoiceRequest)
            ?.enqueue(object : Callback<UpdateChoiceResponse?> {
                override fun onResponse(
                    call: Call<UpdateChoiceResponse?>,
                    response: Response<UpdateChoiceResponse?>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<UpdateChoiceResponse?>, t: Throwable) {
                    onResult(null)
                }
            })
    }
}



