package com.example.android

import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng
import android.util.Log

class Converters {
    @TypeConverter
    fun fromLatLngList(value: List<LatLng>): String {
        val result = value.joinToString(";") { "${it.latitude},${it.longitude}" }
        Log.d("TypeConverter", "fromLatLngList: $value -> $result")
        return result
    }

    @TypeConverter
    fun toLatLngList(value: String): List<LatLng> {
        val result = if (value.isBlank()) emptyList() else value.split(";").map {
            val parts = it.split(",")
            LatLng(parts[0].toDouble(), parts[1].toDouble())
        }
        Log.d("TypeConverter", "toLatLngList: $value -> $result")
        return result
    }

    @TypeConverter
    fun fromActivityType(value: ActivityType): String = value.name

    @TypeConverter
    fun toActivityType(value: String): ActivityType = ActivityType.valueOf(value)
}