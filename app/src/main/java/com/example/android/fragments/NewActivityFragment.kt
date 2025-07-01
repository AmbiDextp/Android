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
import android.widget.Button
import com.example.android.ActivityType
import com.example.android.UserActivity
import com.example.android.MyActivitiesViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.model.LatLng
import kotlin.random.Random
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import android.util.Log
import kotlinx.coroutines.delay

class NewActivityFragment : Fragment() {
    private lateinit var viewModel: MyActivitiesViewModel
    private var selectedActivityType: ActivityType = ActivityType.WALKING

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MyActivitiesViewModel::class.java)
        val recycler = view.findViewById<RecyclerView>(R.id.activity_type_recycler)
        val activityTypes = ActivityType.values().map {
            ActivityTypeAdapter.ActivityType(it.name, R.drawable.ic_sports_logo)
        }
        val adapter = com.example.android.ActivityTypeAdapter(activityTypes) { type ->
            selectedActivityType = ActivityType.valueOf(type.name)
        }
        recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recycler.adapter = adapter

        val startButton = view.findViewById<Button>(R.id.start_activity_btn)
        startButton.setOnClickListener {
            val now = System.currentTimeMillis()
            val duration = Random.nextLong(5 * 60 * 1000, 60 * 60 * 1000) // 5-60 мин
            val startTime = now - duration
            val endTime = 0L // Активность изначально незавершённая
            val coordinates = List(Random.nextInt(3, 10)) {
                LatLng(
                    55.0 + Random.nextDouble(-0.1, 0.1),
                    37.0 + Random.nextDouble(-0.1, 0.1)
                )
            }
            val activity = UserActivity(
                activityType = selectedActivityType,
                startTime = startTime,
                endTime = endTime,
                coordinates = coordinates
            )
            viewLifecycleOwner.lifecycleScope.launch {
                val newId = viewModel.insertActivity(activity)
                Log.d("NewActivityFragment", "Room вернул id=$newId")
                Toast.makeText(requireContext(), "Активность создана!", Toast.LENGTH_SHORT).show()
                // Ждём появления активности с нужным id в LiveData
                var found = false
                repeat(30) { // до 30 раз, с задержкой
                    val act = viewModel.allActivities.value?.find { it.id == newId.toInt() }
                    Log.d("NewActivityFragment", "Пробуем найти активность с id=$newId, найдено: $act")
                    if (act != null) {
                        found = true
                        return@repeat
                    }
                    delay(200)
                }
                if (found) {
                    val fragment = ActivityInProgressFragment.newInstance(newId.toInt())
                    parentFragmentManager.commit {
                        replace(R.id.fragment_container, fragment)
                        addToBackStack(null)
                    }
                } else {
                    Toast.makeText(requireContext(), "Ошибка: активность не найдена!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        const val TAG = "NewActivityFragment"
        fun newInstance(): NewActivityFragment = NewActivityFragment()
    }
} 