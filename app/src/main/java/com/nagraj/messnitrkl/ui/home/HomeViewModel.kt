package com.nagraj.messnitrkl.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _rollNo = MutableLiveData<String>().apply {
        value = ""
    }
    val rollNo: LiveData<String> = _rollNo
}