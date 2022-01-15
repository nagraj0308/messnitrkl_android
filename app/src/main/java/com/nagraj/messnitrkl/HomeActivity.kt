package com.nagraj.messnitrkl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.nagraj.messnitrkl.common.Constants.Companion.CHOICE_CODE
import com.nagraj.messnitrkl.common.Constants.Companion.CHOICE_NAME
import com.nagraj.messnitrkl.common.Constants.Companion.checkTime
import com.nagraj.messnitrkl.common.Constants.Companion.getChoice
import com.nagraj.messnitrkl.select.SearchSelectFragment
import com.nagraj.messnitrkl.common.Constants.Companion.toast
import com.nagraj.messnitrkl.common.DataStorePreference
import com.nagraj.messnitrkl.databinding.ActivityHomeBinding
import com.nagraj.messnitrkl.network.ApiService
import com.nagraj.messnitrkl.network.getstudentchoice.request.GetStudentChoiceRequest
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
    private var isChoiceSelected: Boolean? = false
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
                updateChoice()
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


    private fun updateChoice() {
        binding.btnSendChoice.isEnabled = false
        val updateChoiceRequest = UpdateChoiceRequest(
            rollNo = rollNo,
            choiceCode = CHOICE_CODE[choiceNo],
            recordTime = System.currentTimeMillis().toString(),
        )
        ApiService().updateChoice(updateChoiceRequest) {
            binding.btnSendChoice.isEnabled = true
            if (it?.isTrue == 1 && it.data != null) {
                val time = System.currentTimeMillis()
                val bt = it.data.breakfastRecordTime
                val b = it.data.breakfast
                val lt = it.data.lunchRecordTime
                val l = it.data.lunch
                val st = it.data.snacksRecordTime
                val s = it.data.snacks
                val dt = it.data.dinnerRecordTime
                val d = it.data.dinner
                if (checkTime(bt.toLong(), time)) {
                    binding.tvNextBreakFast.text = getChoice(b)
                } else {
                    binding.tvNextBreakFast.text = getChoice("")
                }
                if (checkTime(lt.toLong(), time)) {
                    binding.tvNextLunch.text = getChoice(l)
                } else {
                    binding.tvNextLunch.text = getChoice("")
                }
                if (checkTime(st.toLong(), time)) {
                    binding.tvNextSnacks.text = getChoice(s)
                } else {
                    binding.tvNextSnacks.text = getChoice("")
                }
                if (checkTime(dt.toLong(), time)) {
                    binding.tvNextDinner.text = getChoice(d)
                } else {
                    binding.tvNextDinner.text = getChoice("")
                }

                toast(this, "your choice changed successfully")

            } else {
                it?.msg?.let { it1 -> toast(this, it1) }
            }
        }
    }

    private fun getStudentChoice() {
        binding.btnSendChoice.isEnabled = false
        val getStudentChoiceRequest = GetStudentChoiceRequest(
            rollNo = rollNo,
        )
        ApiService().getStudentChoice(getStudentChoiceRequest) {
            binding.btnSendChoice.isEnabled = true
            if (it?.isTrue == 1 && it.data != null) {
                val time = System.currentTimeMillis()
                val bt = it.data.breakfastRecordTime
                val b = it.data.breakfast
                val lt = it.data.lunchRecordTime
                val l = it.data.lunch
                val st = it.data.snacksRecordTime
                val s = it.data.snacks
                val dt = it.data.dinnerRecordTime
                val d = it.data.dinner
                if (checkTime(bt.toLong(), time)) {
                    binding.tvNextBreakFast.text = getChoice(b)
                } else {
                    binding.tvNextBreakFast.text = getChoice("")
                }
                if (checkTime(lt.toLong(), time)) {
                    binding.tvNextLunch.text = getChoice(l)
                } else {
                    binding.tvNextLunch.text = getChoice("")
                }
                if (checkTime(st.toLong(), time)) {
                    binding.tvNextSnacks.text = getChoice(s)
                } else {
                    binding.tvNextSnacks.text = getChoice("")
                }
                if (checkTime(dt.toLong(), time)) {
                    binding.tvNextDinner.text = getChoice(d)
                } else {
                    binding.tvNextDinner.text = getChoice("")
                }

                toast(this, "logged in successfully")

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
                    getStudentChoice()
                }
            }
        }
    }

    public fun showSupportBar(b: Boolean) {
        if (b) {
            binding.llOptions.visibility = View.VISIBLE
            binding.llButton.visibility = View.VISIBLE
            supportActionBar?.title = getString(R.string.app_name)
        } else {
            supportActionBar?.title = getString(R.string.select_your_choice)
            binding.llButton.visibility = View.GONE
            binding.llOptions.visibility = View.GONE
        }
    }

    public fun setChoice(i: Int) {
        choiceNo = i
        isChoiceSelected = true
        binding.tvMsg.text = "I will do " + CHOICE_NAME[i]
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