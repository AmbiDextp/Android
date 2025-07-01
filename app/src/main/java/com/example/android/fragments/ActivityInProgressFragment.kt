package com.example.android.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.R
import com.example.android.MyActivitiesViewModel
import com.example.android.UserActivity
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import android.util.Log

class ActivityInProgressFragment : Fragment() {
    private var timer: CountDownTimer? = null
    private var secondsElapsed = 0
    private lateinit var timerText: TextView
    private var activityId: Int = 0
    private lateinit var viewModel: MyActivitiesViewModel
    private var userActivity: UserActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityId = arguments?.getInt(ARG_ACTIVITY_ID) ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_activity_in_progress, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        timerText = view.findViewById(R.id.timer_text)
        val finishButton = view.findViewById<Button>(R.id.finish_activity_btn)
        viewModel = ViewModelProvider(this).get(MyActivitiesViewModel::class.java)
        // Получаем активность из ViewModel (LiveData)
        viewModel.allActivities.observe(viewLifecycleOwner) { activities ->
            Log.d("ActivityInProgress", "allActivities: $activities, ищем id=$activityId")
            userActivity = activities.find { it.id == activityId }
            Log.d("ActivityInProgress", "userActivity найден: $userActivity")
        }
        startTimer()
        finishButton.setOnClickListener {
            Log.d("ActivityInProgress", "Клик по кнопке завершить, userActivity=$userActivity")
            timer?.cancel()
            userActivity?.let { activity ->
                viewLifecycleOwner.lifecycleScope.launch {
                    val newEndTime = System.currentTimeMillis()
                    Log.d("ActivityInProgress", "Завершение активности id=${activity.id}, старое endTime=${activity.endTime}, новое endTime=$newEndTime")
                    val updated = activity.copy(endTime = newEndTime)
                    val resultId = viewModel.insertActivity(updated)
                    Log.d("ActivityInProgress", "insertActivity вернул id=$resultId")
                    Toast.makeText(requireContext(), "Активность завершена!", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.popBackStack()
                }
            }
        }
    }

    private fun startTimer() {
        timer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                secondsElapsed++
                timerText.text = formatTime(secondsElapsed)
            }
            override fun onFinish() {}
        }.start()
    }

    private fun formatTime(seconds: Int): String {
        val min = seconds / 60
        val sec = seconds % 60
        return String.format("%02d:%02d", min, sec)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer?.cancel()
    }

    companion object {
        private const val ARG_ACTIVITY_ID = "activity_id"
        fun newInstance(activityId: Int): ActivityInProgressFragment {
            val fragment = ActivityInProgressFragment()
            val args = Bundle()
            args.putInt(ARG_ACTIVITY_ID, activityId)
            fragment.arguments = args
            return fragment
        }
    }
} 