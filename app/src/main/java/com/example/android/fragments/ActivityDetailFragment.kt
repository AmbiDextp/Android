package com.example.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.android.ActivityAdapter
import com.example.android.R

class ActivityDetailFragment : Fragment() {
    companion object {
        fun newInstance(activity: ActivityAdapter.ActivityItem.Activity): ActivityDetailFragment {
            val fragment = ActivityDetailFragment()
            val args = Bundle()
            args.putString("title", activity.title)
            args.putString("ago", activity.ago)
            args.putString("duration", activity.duration)
            args.putString("distance", activity.distance)
            args.putString("author", activity.author)
            // start_time и finish_time можно добавить позже
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_activity_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = arguments?.getString("title") ?: ""
        val ago = arguments?.getString("ago") ?: ""
        val duration = arguments?.getString("duration") ?: ""
        val distance = arguments?.getString("distance") ?: ""
        val author = arguments?.getString("author") ?: ""
        val startTime = "14:49"
        val finishTime = "16:31"

        view.findViewById<TextView>(R.id.detail_title).text = title
        view.findViewById<TextView>(R.id.detail_distance).text = distance
        view.findViewById<TextView>(R.id.detail_ago).text = ago
        view.findViewById<TextView>(R.id.detail_duration_label).text = duration
        view.findViewById<TextView>(R.id.detail_start_time).text = startTime
        view.findViewById<TextView>(R.id.detail_finish_time).text = finishTime
        val authorView = view.findViewById<TextView>(R.id.detail_author)
        if (author.isNotEmpty()) {
            authorView.text = "@" + author
            authorView.visibility = View.VISIBLE
            view.findViewById<EditText>(R.id.detail_comment).setText("Я бежал очень сильно, ты так не сможешь")
            view.findViewById<EditText>(R.id.detail_comment).isEnabled = false
        } else {
            authorView.visibility = View.GONE
            view.findViewById<EditText>(R.id.detail_comment).setText("")
            view.findViewById<EditText>(R.id.detail_comment).isEnabled = true
        }
        view.findViewById<ImageButton>(R.id.imageButton).setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}
