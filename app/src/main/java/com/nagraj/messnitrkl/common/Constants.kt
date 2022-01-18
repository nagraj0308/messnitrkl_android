package com.nagraj.messnitrkl.common

import android.content.Context
import android.util.Log
import android.widget.Toast

class Constants {
    companion object {
        val HOSTELS = arrayOf("CVR", "DBA", "GDB", "HB", "KMS", "MSS", "SD", "VS")
        val BASE_URL = "https://messnitrklserver.herokuapp.com"

        val CHOICE_CODE = arrayOf(
            "BYG",
            "BYR",
            "BNG",
            "LYG",
            "LYR",
            "LNG",
            "SYG",
            "SYR",
            "SNG",
            "DYG",
            "DYR",
            "DNG"
        )
        val CHOICE_NAME = arrayOf(
            "Breakfast Veg",
            "Breakfast Non Veg",
            "No Breakfast",
            "Lunch Veg",
            "Lunch Non Veg",
            "No Lunch",
            "Snacks Veg",
            "Snacks Non Veg",
            "No Snacks",
            "Dinner Veg",
            "Dinner Non Veg",
            "No Dinner"
        )

        public fun getChoice(s: String): String {
            if (s == "YG") {
                return "Veg"
            } else if (s == "YR") {
                return "Non Veg"
            } else if (s == "") {
                return "----"
            } else {
                return "Not Taking"
            }
        }


        //datastore
        val DATASTORE_NAME = "credentials"
        val PREF_IS_LOGGED_IN = "pref_is_logged_in"
        val PREF_HOSTEL = "pref_hostel"
        val PREF_MOBILE_NO = "pref_mobile_no"
        val PREF_ROLL_NO = "pref_roll_no"

        //time calculation
        private const val ms_in_24hr = 24 * 60 * 60 * 1000
        public fun checkTime(t1: Long, t2: Long): Boolean {
            return (t1 + ms_in_24hr) > t2
        }

        public fun toast(context: Context, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        }

        public fun log(msg: String) {
            Log.d("NAGRAJ", "log: $msg")
        }
    }
}