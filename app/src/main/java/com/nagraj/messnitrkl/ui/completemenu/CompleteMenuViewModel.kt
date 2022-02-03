package com.nagraj.messnitrkl.ui.completemenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CompleteMenuViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Complete Menu Fragment"
    }
    val text: LiveData<String> = _text
}