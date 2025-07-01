package com.example.android

import android.util.Log
import androidx.lifecycle.LiveData

class ActivityRepository(private val activityDao: ActivityDao) {
    val allActivities: LiveData<List<UserActivity>> = activityDao.getAllActivities()

    suspend fun insert(activity: UserActivity): Long {
        Log.d("ActivityRepository", "insert: $activity")
        return activityDao.insertActivity(activity)
    }
}