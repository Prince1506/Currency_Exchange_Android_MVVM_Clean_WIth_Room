package com.mvvm_clean.currency_exchange.core.source.disk.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.domain.models.CurrencyRateInfo
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.domain.models.tableName

@Dao
interface CurrencyRateListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCurrencyRates(currencyListInfo: CurrencyRateInfo)

    @Query("DELETE FROM $tableName")
    fun deleteAll()

    @Query("SELECT * FROM $tableName")
    fun getAllCurrencyRates(): List<CurrencyRateInfo>

    @Query("SELECT * FROM $tableName WHERE source=:source")
    fun getCurrencyRateById(source: String): CurrencyRateInfo

}