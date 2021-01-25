package com.mvvm_clean.currency_exchange.core.source.disk.typeConverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.domain.models.CurrencyRateInfo
import java.util.*

class ListConverter {
    private val gson = Gson()

    @TypeConverter
    fun stringToList(data: String?): List<CurrencyRateInfo> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<CurrencyRateInfo>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun ListToString(someObjects: List<CurrencyRateInfo>): String {
        return gson.toJson(someObjects)
    }
}