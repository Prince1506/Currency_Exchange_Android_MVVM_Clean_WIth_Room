package com.mvvm_clean.currency_exchange.core.source.disk.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.repo.CurrencyRateInfo
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.repo.tableName

@Dao
interface CurrencyRateListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCurrencyRates(currencyListInfo: CurrencyRateInfo)

    @Query("DELETE FROM $tableName")
    fun deleteAll()

    @Query("SELECT * FROM $tableName")
    fun getAllCurrencyRates(): List<CurrencyRateInfo>

    @Query("SELECT * FROM $tableName WHERE id=:id")
    fun getCurrencyRateById(id: Int): CurrencyRateInfo

}