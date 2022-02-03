package com.nagraj.messnitrkl.ui
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.nagraj.messnitrkl.R
import com.nagraj.messnitrkl.common.Constants
import com.nagraj.messnitrkl.common.Constants.Companion.checkTime
import com.nagraj.messnitrkl.common.Constants.Companion.getChoice
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


class HomeActivity : AppCompatActivity() {
    private lateinit var dataStoreManager: DataStorePreference
    private lateinit var binding: ActivityHomeBinding
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private var rollNo: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataStoreManager = DataStorePreference(this)
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)

        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionBarDrawerToggle.syncState()

        ArrayAdapter(
            this, R.layout.view_spinner, Constants.CHOICE_SP
        ).also {
            it.setDropDownViewResource(R.layout.item_spinner)
            binding.spChangeBf.adapter = it
            binding.spChangeLunch.adapter = it
            binding.spChangeSnacks.adapter = it
            binding.spChangeDinner.adapter = it
        }
        binding.spChangeBf.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 != 0) {
                    updateChoice("B" + Constants.CHOICE[p2 - 1])
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        binding.spChangeLunch.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 != 0) {
                    updateChoice("L" + Constants.CHOICE[p2 - 1])
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        binding.spChangeSnacks.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (p2 != 0) {
                        updateChoice("S" + Constants.CHOICE[p2 - 1])
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
        binding.spChangeDinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (p2 != 0) {
                        updateChoice("D" + Constants.CHOICE[p2 - 1])
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
        binding.btnLogout.setOnClickListener {
            logOut()
        };
        getStoreValues()
    }


    private fun logOut() {
        lifecycleScope.launch(Dispatchers.IO) {
            dataStoreManager.setLogout()
            gotoLoginPage()
        }
    }

    private fun gotoLoginPage() {
        val loginActivity = Intent(this, LoginActivity::class.java)
        finish()
        startActivity(loginActivity)
    }


    private fun updateChoice(choiceCode: String) {
        enableAllSpinner(false)
        binding.progressBar.visibility = View.VISIBLE
        val updateChoiceRequest = UpdateChoiceRequest(
            rollNo = rollNo,
            choiceCode = choiceCode,
            recordTime = System.currentTimeMillis().toString(),
        )
        ApiService().updateChoice(updateChoiceRequest) {
            binding.progressBar.visibility = View.GONE
            setToChangeAllSpinner()
            enableAllSpinner(true)

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

    private fun enableAllSpinner(b: Boolean) {
        if (binding != null) {
            binding.spChangeBf.isEnabled = b
            binding.spChangeLunch.isEnabled = b
            binding.spChangeSnacks.isEnabled = b
            binding.spChangeDinner.isEnabled = b
        }
    }

    private fun setToChangeAllSpinner() {
        if (binding != null) {
            binding.spChangeBf.setSelection(0)
            binding.spChangeLunch.setSelection(0)
            binding.spChangeSnacks.setSelection(0)
            binding.spChangeDinner.setSelection(0)
        }
    }


    private fun getStudentChoice() {
        binding.progressBar.visibility = View.VISIBLE
        enableAllSpinner(false)
        val getStudentChoiceRequest = GetStudentChoiceRequest(
            rollNo = rollNo,
        )
        ApiService().getStudentChoice(getStudentChoiceRequest) {
            enableAllSpinner(true)
            binding.progressBar.visibility = View.GONE
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

}