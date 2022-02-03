package com.nagraj.messnitrkl.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.nagraj.messnitrkl.common.DataStorePreference
import com.nagraj.messnitrkl.databinding.ActivitySplashBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashActivity : AppCompatActivity() {
    private lateinit var dataStoreManager: DataStorePreference
    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        dataStoreManager = DataStorePreference(this)
        getStoreValues()
    }

    private fun gotoHomeActivity() {
        val homeActivity = Intent(this, AppActivity::class.java)
        finish()
        startActivity(homeActivity)
    }

    private fun gotoLoginActivity() {
        val loginActivity = Intent(this, LoginActivity::class.java)
        finish()
        startActivity(loginActivity)
    }

    private fun getStoreValues() {
        lifecycleScope.launch(
            Dispatchers.IO
        ) {
            dataStoreManager.isLoggedIn.collect {
                withContext(Dispatchers.Main) {
                    delay(2000)
                    if (it) {
                        gotoHomeActivity()
                    } else {
                        gotoLoginActivity()
                    }
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}