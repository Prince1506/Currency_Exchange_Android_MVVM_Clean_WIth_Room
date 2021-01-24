package com.mvvm_clean.currency_exchange.core.source.disk.typeConverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mvvm_clean.currency_exchange.features.canada_facts.data.repo.CanadaFactsInfo
import java.util.*

class TaskEntityConverter {
    private val gson = Gson()
    @TypeConverter
    fun stringToList(data: String?): List<CanadaFactsInfo> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<CanadaFactsInfo>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun ListToString(someObjects: List<CanadaFactsInfo>): String {
        return gson.toJson(someObjects)
    }
}