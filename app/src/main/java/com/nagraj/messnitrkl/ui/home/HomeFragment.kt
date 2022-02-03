package com.nagraj.messnitrkl.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.nagraj.messnitrkl.R
import com.nagraj.messnitrkl.common.Constants
import com.nagraj.messnitrkl.common.DataStorePreference
import com.nagraj.messnitrkl.databinding.FragmentHomeBinding
import com.nagraj.messnitrkl.network.ApiService
import com.nagraj.messnitrkl.network.getstudentchoice.request.GetStudentChoiceRequest
import com.nagraj.messnitrkl.network.updatechoice.request.UpdateChoiceRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    private lateinit var dataStoreManager: DataStorePreference
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private var rollNo: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStoreManager = context?.let { DataStorePreference(it) }!!
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
//        homeViewModel.rollNo.observe(viewLifecycleOwner) {
//            rollNo = it
//        }


        ArrayAdapter(
            requireContext(), R.layout.view_spinner, Constants.CHOICE_SP
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
        getStoreValues()
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
                if (Constants.checkTime(bt.toLong(), time)) {
                    binding.tvNextBreakFast.text = Constants.getChoice(b)
                } else {
                    binding.tvNextBreakFast.text = Constants.getChoice("")
                }
                if (Constants.checkTime(lt.toLong(), time)) {
                    binding.tvNextLunch.text = Constants.getChoice(l)
                } else {
                    binding.tvNextLunch.text = Constants.getChoice("")
                }
                if (Constants.checkTime(st.toLong(), time)) {
                    binding.tvNextSnacks.text = Constants.getChoice(s)
                } else {
                    binding.tvNextSnacks.text = Constants.getChoice("")
                }
                if (Constants.checkTime(dt.toLong(), time)) {
                    binding.tvNextDinner.text = Constants.getChoice(d)
                } else {
                    binding.tvNextDinner.text = Constants.getChoice("")
                }
                Constants.toast(context, "your choice changed successfully")
            } else {
                it?.msg?.let { it1 -> Constants.toast(context, it1) }
            }
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
                if (Constants.checkTime(bt.toLong(), time)) {
                    binding.tvNextBreakFast.text = Constants.getChoice(b)
                } else {
                    binding.tvNextBreakFast.text = Constants.getChoice("")
                }
                if (Constants.checkTime(lt.toLong(), time)) {
                    binding.tvNextLunch.text = Constants.getChoice(l)
                } else {
                    binding.tvNextLunch.text = Constants.getChoice("")
                }
                if (Constants.checkTime(st.toLong(), time)) {
                    binding.tvNextSnacks.text = Constants.getChoice(s)
                } else {
                    binding.tvNextSnacks.text = Constants.getChoice("")
                }
                if (Constants.checkTime(dt.toLong(), time)) {
                    binding.tvNextDinner.text = Constants.getChoice(d)
                } else {
                    binding.tvNextDinner.text = Constants.getChoice("")
                }
//                Constants.toast(context, "logged in successfully")
            }
        }
    }


}