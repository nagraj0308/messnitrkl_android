package com.nagraj.messnitrkl.ui.completemenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.nagraj.messnitrkl.databinding.FragmentCompleteMenuBinding

class CompleteMenuFragment : Fragment() {

    private lateinit var binding: FragmentCompleteMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val completeMenuViewModel =
            ViewModelProvider(this).get(CompleteMenuViewModel::class.java)

        binding = FragmentCompleteMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textGallery
        completeMenuViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

}