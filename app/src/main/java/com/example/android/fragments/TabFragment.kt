package com.example.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.android.R

class TabFragment : Fragment() {
    companion object {
        fun newInstance(text: String): TabFragment {
            return TabFragment().apply {
                arguments = bundleOf("text" to text)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_tab, container, false).apply {
            findViewById<TextView>(R.id.text_view).text = arguments?.getString("text")
        }
    }
}