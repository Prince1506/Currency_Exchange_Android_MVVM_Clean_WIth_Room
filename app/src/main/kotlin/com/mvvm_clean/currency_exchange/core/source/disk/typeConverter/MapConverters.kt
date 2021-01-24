package com.mvvm_clean.currency_exchange.core.source.disk.typeConverter


import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class MapConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromTimestamp(json: String?): Map<String?, Double?>? {
        val type: Type = object : TypeToken<Map<String?, Double?>?>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun dateToTimestamp(map: Map<String?, Double?>?): String? {
        return gson?.toJson(map);
    }
}