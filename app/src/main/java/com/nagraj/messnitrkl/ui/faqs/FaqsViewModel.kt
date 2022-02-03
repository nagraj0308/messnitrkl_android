package com.nagraj.messnitrkl.ui.faqs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FaqsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is FAQs Fragment"
    }
    val text: LiveData<String> = _text
}