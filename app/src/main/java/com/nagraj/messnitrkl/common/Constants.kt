package com.nagraj.messnitrkl.common

import android.content.Context
import android.util.Log
import android.widget.Toast

class Constants {
    companion object {
        val HOSTELS = arrayOf("CVR", "DBA", "GDB", "HB", "KMS", "MSS", "SD", "VS")
        val BASE_URL = "https://messnitrkl.herokuapp.com"


        //datastore
        val DATASTORE_NAME = "credentials"
        val PREF_IS_LOGGED_IN = "pref_is_logged_in"
        val PREF_HOSTEL = "pref_hostel"
        val PREF_MOBILE_NO = "pref_mobile_no"
        val PREF_ROLL_NO = "pref_roll_no"

        public fun toast(context: Context, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        }

        public fun log(msg: String) {
            Log.d("NAGRAJ", "log: $msg")
        }
    }
}