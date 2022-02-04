package com.nagraj.messnitrkl.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nagraj.messnitrkl.network.ApiService
import com.nagraj.messnitrkl.network.getstudentchoice.request.GetStudentChoiceRequest
import com.nagraj.messnitrkl.network.getstudentchoice.response.GetStudentChoiceResponse

class HomeViewModel : ViewModel() {

    private val choice: MutableLiveData<GetStudentChoiceResponse> by lazy {
        MutableLiveData<GetStudentChoiceResponse>().also {
//            loadChoice(rollNo = )
        }
    }

    fun getChoice(rollNo: String): LiveData<GetStudentChoiceResponse> {
        return choice
    }

    private fun loadChoice(rollNo: String) {
        val getStudentChoiceRequest = GetStudentChoiceRequest(
            rollNo = rollNo,
        )
       ApiService().getStudentChoice(getStudentChoiceRequest){
           choice.postValue(it)
       }
    }
}