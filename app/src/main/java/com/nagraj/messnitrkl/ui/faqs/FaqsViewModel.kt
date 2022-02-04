package com.nagraj.messnitrkl.ui.faqs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FaqsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "<p><b>What does this app do?</b></p><br>" +
                "<p>This app collects your choice of mess food if you are going to eat or not and" +
                " if going to eat then veg or non-veg and send the data to your mess and mess staff" +
                " will be made food accordingly. </p><br>"+

                "<p><b>Is this app is official?</b></p><br>" +
                "<p>No, this app is unofficial app and we are doing it free, we are trying to make this official. </p><br>"+

                "<p><b>What if I have not selected any choice?</b></p><br>" +
                "<p>As, we have cleared above this app is an unofficial app, you can goto to your mess eat " +
                "whatever you want, we are just collecting data to approximate. </p><br>"+

                "<p><b>What if I have selected Not Taking food, can I take food?</b></p><br>" +
                "<p>Yes, As we have cleared above this app is an unofficial app," +
                "mess is yours, you can take whatever you want, we are just collecting data to " +
                "approximate. </p><br>"

    }
    val text: LiveData<String> = _text
}