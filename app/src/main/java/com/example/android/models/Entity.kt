package com.example.android

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity(tableName = "activities")
data class UserActivity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val activityType: ActivityType,
    val startTime: Long, // timestamp в миллисекундах
    val endTime: Long,
    val coordinates: List<LatLng> // потребуется TypeConverter
)