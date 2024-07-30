package com.t4zb.e_commerce.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.t4zb.e_commerce.data.model.ProductRoom
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromProductList(value: List<ProductRoom>?): String {
        val gson = Gson()
        val type = object : TypeToken<List<ProductRoom>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toProductList(value: String): List<ProductRoom>? {
        val gson = Gson()
        val type = object : TypeToken<List<ProductRoom>>() {}.type
        return gson.fromJson(value, type)
    }
}