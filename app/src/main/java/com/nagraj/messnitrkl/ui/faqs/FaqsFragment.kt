package com.nagraj.messnitrkl.ui.faqs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.nagraj.messnitrkl.databinding.FragmentFaqsBinding

class FaqsFragment : Fragment() {

    private lateinit var binding: FragmentFaqsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val faqsViewModel =
            ViewModelProvider(this).get(FaqsViewModel::class.java)

        binding = FragmentFaqsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSlideshow
        faqsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

}