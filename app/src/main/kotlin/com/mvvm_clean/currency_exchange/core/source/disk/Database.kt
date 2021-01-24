package com.mvvm_clean.currency_exchange.core.source.disk

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mvvm_clean.currency_exchange.core.source.disk.dao.CurrentCurrencyExchangeRateListDao
import com.mvvm_clean.currency_exchange.core.source.disk.typeConverter.DateConverters
import com.mvvm_clean.currency_exchange.core.source.disk.typeConverter.MapConverters
import com.mvvm_clean.currency_exchange.core.source.disk.typeConverter.TaskEntityConverter
import com.mvvm_clean.currency_exchange.features.canada_facts.data.repo.CanadaFactsInfo
import com.mvvm_clean.currency_exchange.features.canada_facts.data.repo.TaskResponseEntity

@Database(
    entities = [CanadaFactsInfo::class], version = 2, exportSchema = false
)
@TypeConverters(
    TaskEntityConverter::class,
    DateConverters::class,
    MapConverters::class
)
abstract class Database : RoomDatabase() {

    abstract fun TaskListDao(): CurrentCurrencyExchangeRateListDao

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
