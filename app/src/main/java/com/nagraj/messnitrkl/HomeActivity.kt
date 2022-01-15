package com.nagraj.messnitrkl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.nagraj.messnitrkl.select.SearchSelectFragment
import com.nagraj.messnitrkl.common.Constants.Companion.toast
import com.nagraj.messnitrkl.common.DataStorePreference
import com.nagraj.messnitrkl.databinding.ActivityHomeBinding
import com.nagraj.messnitrkl.network.ApiService
import com.nagraj.messnitrkl.network.updatechoice.request.UpdateChoiceRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeActivity : AppCompatActivity() {
    private lateinit var dataStoreManager: DataStorePreference
    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!
    private var isChoiceSelected: Boolean? = true
    private var rollNo: String? = ""
    private var choiceCode: String? = ""
    private var choiceList: ArrayList<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataStoreManager = DataStorePreference(this)

        binding.btnSendChoice.setOnClickListener {
            if (isChoiceSelected == true) {
                registerStudent()
            } else {
                toast(this, "Plz select a choice...")
            }
        };
        binding.tvMsg.setOnClickListener {
            val fragment = SearchSelectFragment.newInstance(choiceList)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frag_search_select, fragment)
                .commit()
        };
        getStoreValues()
    }


    private fun registerStudent() {
        binding.btnSendChoice.isEnabled = false
        val updateChoiceRequest = UpdateChoiceRequest(
            rollNo = rollNo,
            choiceCode = "BYG",
            recordTime = System.currentTimeMillis().toString(),
        )
        ApiService().updateChoice(updateChoiceRequest) {
            binding.btnSendChoice.isEnabled = true
            if (it?.isTrue == 1) {
                toast(this, "hi")
            } else {
                it?.msg?.let { it1 -> toast(this, it1) }
            }
        }
    }

    private fun getStoreValues() {
        lifecycleScope.launch(
            Dispatchers.IO
        ) {
            dataStoreManager.getRollNo.collect{
                withContext(Dispatchers.Main) {
                    rollNo = it
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}