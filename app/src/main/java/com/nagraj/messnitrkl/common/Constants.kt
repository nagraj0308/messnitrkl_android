package com.nagraj.messnitrkl.common

import android.content.Context
import android.util.Log
import android.widget.Toast

class Constants {
    companion object {
        val HOSTELS = arrayOf("CVR", "DBA", "GDB", "HB", "KMS", "MSS", "SD", "VS")
        const val BASE_URL = "https://messnitrklserver.herokuapp.com"
        const val APK_SHARE_MSG = "Hey!! I am using our app Mess NITRKL for saving food please you also install, link:- https://play.google.com/store/apps/details?id=com.nagraj.messnitrkl Thanks!!"

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

        val CHOICE = arrayOf(
            "YG",
            "YR",
            "NG",
        )
        val CHOICE_SP = arrayOf(
            "Change",
            "Veg",
            "Non Veg",
            "Not Taking",
        )

        fun getChoice(s: String): String {
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
        const val DATASTORE_NAME = "credentials"
        const val PREF_IS_LOGGED_IN = "pref_is_logged_in"
        const val PREF_HOSTEL = "pref_hostel"
        const val PREF_ROLL_NO = "pref_roll_no"

        //time calculation
        private const val ms_in_24hr = 24 * 60 * 60 * 1000
        fun checkTime(t1: Long, t2: Long): Boolean {
            return (t1 + ms_in_24hr) > t2
        }

        fun toast(context: Context?, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        }

        fun log(msg: String) {
            Log.d("NAGA", "log: $msg")
        }
    }
}