package com.nagraj.messnitrkl

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.nagraj.messnitrkl.common.Constants.Companion.CHOICE_CODE
import com.nagraj.messnitrkl.common.Constants.Companion.CHOICE_NAME
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
import kotlin.collections.ArrayList

class HomeActivity : AppCompatActivity() {
    private lateinit var dataStoreManager: DataStorePreference
    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!
    private var isChoiceSelected: Boolean? = true
    private var rollNo: String? = ""
    private var choiceNo: Int = 0
    private lateinit var fragment: SearchSelectFragment
    private var choiceNameList: ArrayList<String> = CHOICE_NAME.toCollection(ArrayList())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataStoreManager = DataStorePreference(this)
        showSupportBar(true)
        binding.btnSendChoice.setOnClickListener {
            if (isChoiceSelected == true) {
                registerStudent()
            } else {
                toast(this, "Plz select a choice...")
            }
        };
        binding.tvMsg.setOnClickListener {
            fragment = SearchSelectFragment.newInstance(choiceNameList)
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
            choiceCode = CHOICE_CODE[choiceNo],
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
            dataStoreManager.getRollNo.collect {
                withContext(Dispatchers.Main) {
                    rollNo = it
                }
            }
        }
    }

    public fun showSupportBar(b: Boolean) {
        if (b) {
            binding.llButton.visibility = View.VISIBLE
            supportActionBar?.title = getString(R.string.app_name)
        } else {
            supportActionBar?.title = getString(R.string.select_your_choice)
            binding.llButton.visibility = View.GONE
        }
    }

    public fun setChoice(i: Int) {
        choiceNo = i
        binding.tvMsg.text ="I will do "+CHOICE_NAME[i]
        showSupportBar(true)
        supportFragmentManager
            .beginTransaction()
            .remove(fragment)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}