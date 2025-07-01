package com.example.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.ActivityAdapter
import com.example.android.MyActivitiesViewModel
import com.example.android.R
import android.util.Log

class MyActivitiesFragment : Fragment() {
    private lateinit var viewModel: MyActivitiesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("MyActivitiesFragment", "onCreateView called")
        viewModel = ViewModelProvider(requireActivity()).get(MyActivitiesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_my_activities, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = ActivityAdapter("Мои") { activityItem ->
            if (activityItem is ActivityAdapter.ActivityItem.Activity) {
                val fragment = com.example.android.fragments.ActivityDetailFragment.newInstance(activityItem)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.allActivities.observe(viewLifecycleOwner, { activities ->
            Log.d("MyActivitiesFragment", "activities size: ${activities?.size}, ids: ${activities?.map { it.id }}")
            Log.d("MyActivitiesFragment", "activities full: $activities")
            activities?.let {
                adapter.submitList(it.map { activity ->
                    ActivityAdapter.ActivityItem.Activity(
                        ago = "Недавно",
                        title = activity.activityType.name,
                        distance = "0 км",
                        duration = "0 мин",
                        author = "me"
                    )
                })
            }
        })

        return root
    }

    companion object {
        const val TAG = "MyActivitiesFragment"
    }
}