package com.example.android.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.android.R

class ProfileFragment : Fragment() {
    companion object {
        const val TAG = "profile"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile, container, false).apply {
            findViewById<TextView>(R.id.text_view).text = "Профиль"
        }
    }
}