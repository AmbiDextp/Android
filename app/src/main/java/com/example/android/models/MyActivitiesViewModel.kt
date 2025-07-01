package com.example.android

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData


class MyActivitiesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ActivityRepository
    val allActivities: LiveData<List<UserActivity>>

    init {
        val activityDao = AppDatabase.getDatabase(application).activityDao()
        repository = ActivityRepository(activityDao)
        allActivities = repository.allActivities
    }

    suspend fun insertActivity(activity: UserActivity): Long {
        return repository.insert(activity)
    }
}