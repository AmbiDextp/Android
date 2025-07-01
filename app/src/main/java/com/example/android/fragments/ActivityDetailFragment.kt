package com.example.android.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.android.ActivityAdapter

class ActivityDetailFragment : Fragment() {
    companion object {
        fun newInstance(activity: ActivityAdapter.ActivityItem.Activity): ActivityDetailFragment {
            val fragment = ActivityDetailFragment()
            val args = Bundle()
            args.putString("title", activity.title)
            args.putString("ago", activity.ago)
            args.putString("duration", activity.duration)
            args.putString("distance", activity.distance)
            fragment.arguments = args
            return fragment
        }
    }
}