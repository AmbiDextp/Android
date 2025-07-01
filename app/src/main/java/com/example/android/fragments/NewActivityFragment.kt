package com.example.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.ActivityTypeAdapter

class NewActivityFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recycler = view.findViewById<RecyclerView>(R.id.activity_type_recycler)
        recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recycler.adapter = ActivityTypeAdapter(
            listOf(
                ActivityTypeAdapter.ActivityType("Бег", R.drawable.ic_delete_logo),
                ActivityTypeAdapter.ActivityType("Велосипед", R.drawable.ic_share_logo),
                ActivityTypeAdapter.ActivityType("Серфинг", R.drawable.ic_delete_logo)
            )
        ) { /* ничего не делаем при клике */ }
    }

    companion object {
        const val TAG = "NewActivityFragment"
        fun newInstance(): NewActivityFragment = NewActivityFragment()
    }
} 