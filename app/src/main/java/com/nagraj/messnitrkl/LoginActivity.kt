package com.nagraj.messnitrkl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import com.nagraj.messnitrkl.common.Constants
import com.nagraj.messnitrkl.common.Constants.Companion.toast
import com.nagraj.messnitrkl.common.DataStorePreference
import com.nagraj.messnitrkl.databinding.ActivityLoginBinding
import com.nagraj.messnitrkl.network.ApiService
import com.nagraj.messnitrkl.network.register.request.RegisterRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginActivity : AppCompatActivity() {

    private lateinit var dataStoreManager: DataStorePreference
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    private var selectedHostel = Constants.HOSTELS[0]
    private var rollNo: String = ""
    private var mobileNo: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        dataStoreManager = DataStorePreference(this)

        ArrayAdapter(
            this, android.R.layout.simple_spinner_item, Constants.HOSTELS
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spHostel.adapter = it
        }


        binding.spHostel.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedHostel = Constants.HOSTELS[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                selectedHostel = Constants.HOSTELS[0]
            }
        }

        binding.btnLogin.setOnClickListener {
            if (verify()) {
                registerStudent()
            }
        };
    }

    private fun verify(): Boolean {
        rollNo = binding.etRollNo.text.toString().uppercase()
        mobileNo = binding.etMobile.text.toString()
        if (rollNo.length != 9) {
            toast(this, "Please enter correct roll no..")
            return false
        }
        if (mobileNo.length != 10) {
            toast(this, "Please enter correct mobile no..")
            return false
        }
        return true
    }

    private fun gotoHomePage() {
        val homeActivity = Intent(this, HomeActivity::class.java)
        finish()
        startActivity(homeActivity)
    }

    private fun registerStudent() {
        binding.btnLogin.isEnabled = false
        binding.progressBar.visibility = View.VISIBLE
        val registerRequest = RegisterRequest(
            rollNo = rollNo,
            mobileNo = mobileNo,
            hostel = selectedHostel,
        )
        ApiService().registerStudent(registerRequest) {
            binding.progressBar.visibility = View.GONE
            binding.btnLogin.isEnabled = true
            if (it != null) {
                if (it.isTrue == 1) {
                    lifecycleScope.launch {
                        setStoreValues(rollNo, mobileNo, selectedHostel)
                        gotoHomePage()
                    }
                } else {
                    if (it.msg != null) {
                        toast(this, it.msg)
                    }
                }
            } else {
                toast(this, "No data")
            }
        }
    }

    private fun setStoreValues(
        rollNo: String,
        mobileNo: String,
        hostel: String
    ) {
        lifecycleScope.launch(Dispatchers.IO) {
            dataStoreManager.setLoggedIn(true)
            dataStoreManager.setHostel(hostel)
            dataStoreManager.setMobileNo(mobileNo)
            dataStoreManager.setRollNo(rollNo)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}