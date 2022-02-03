package com.nagraj.messnitrkl.ui.faqs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FaqsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Coming soon..."
    }
    val text: LiveData<String> = _text
}