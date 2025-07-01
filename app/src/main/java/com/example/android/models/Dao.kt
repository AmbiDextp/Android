package com.example.android

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy

@Dao
interface ActivityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(activity: UserActivity): Long

    @Query("SELECT * FROM activities ORDER BY startTime DESC")
    fun getAllActivities(): LiveData<List<UserActivity>>
}