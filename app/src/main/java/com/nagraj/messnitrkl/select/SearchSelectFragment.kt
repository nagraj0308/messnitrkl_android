package com.nagraj.messnitrkl.select;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nagraj.messnitrkl.databinding.FragmentSearchSelectBinding


class SearchSelectFragment() : Fragment() {
    private var _binding: FragmentSearchSelectBinding? = null
    private val binding get() = _binding!!
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<SearchSelectAdapter.ViewHolder>? = null
    private var choiceList: ArrayList<String>? = null


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
        _binding = FragmentSearchSelectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            layoutManager = LinearLayoutManager(activity)
        }
        arguments?.getStringArrayList(ARG_CHOICE_LIST)?.let {
            choiceList = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}










