package com.mvvm_clean.currency_exchange.core.source.disk

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mvvm_clean.currency_exchange.core.source.disk.dao.CurrencyRateListDao
import com.mvvm_clean.currency_exchange.core.source.disk.typeConverter.MapConverters
import com.mvvm_clean.currency_exchange.core.source.disk.typeConverter.ListConverter
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.domain.models.CurrencyRateInfo

@Database(
    entities = [CurrencyRateInfo::class], version = 2, exportSchema = false
)
@TypeConverters(
    ListConverter::class,
    MapConverters::class
)
abstract class Database : RoomDatabase() {

    abstract fun CurrencyRateDao(): CurrencyRateListDao

    companion object {
        private const val DATABASE_NAME: String = "CurrencyExchangeRateList_db"

        fun createInstance(appContext: Application):
                com.mvvm_clean.currency_exchange.core.source.disk.Database = Room.databaseBuilder(
            appContext,
            com.mvvm_clean.currency_exchange.core.source.disk.Database::class.java,
            DATABASE_NAME
        ).build()
    }

}
