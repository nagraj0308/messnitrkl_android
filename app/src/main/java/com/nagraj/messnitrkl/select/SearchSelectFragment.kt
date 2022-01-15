package com.nagraj.messnitrkl.select;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nagraj.messnitrkl.HomeActivity
import com.nagraj.messnitrkl.common.Constants.Companion.log
import com.nagraj.messnitrkl.common.Constants.Companion.toast
import com.nagraj.messnitrkl.databinding.FragmentSearchSelectBinding


class SearchSelectFragment() : Fragment(), (Int) -> Unit {
    private var _binding: FragmentSearchSelectBinding? = null
    private val binding get() = _binding!!
    private lateinit var layoutManager: LinearLayoutManager
    private var adapter: RecyclerView.Adapter<SearchSelectAdapter.ViewHolder>? = null
    private lateinit var choiceList: ArrayList<String>


    companion object {
        private const val ARG_CHOICE_LIST = "arg_choice_list"
        fun newInstance(selected: ArrayList<String>?): SearchSelectFragment {
            return SearchSelectFragment().apply {
                arguments = bundleOf(ARG_CHOICE_LIST to selected)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as HomeActivity).showSupportBar(false)
        _binding = FragmentSearchSelectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getStringArrayList(ARG_CHOICE_LIST)?.let {
            choiceList = it
            binding.rvSearchSelect.layoutManager = LinearLayoutManager(activity)
            (binding.rvSearchSelect.layoutManager as LinearLayoutManager).orientation =
                LinearLayoutManager.VERTICAL


            val adapter = SearchSelectAdapter(choiceList, this)

            binding.rvSearchSelect.adapter = adapter


        }

    }

    override fun invoke(p1: Int) {
        (activity as HomeActivity).setChoice(p1)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}










