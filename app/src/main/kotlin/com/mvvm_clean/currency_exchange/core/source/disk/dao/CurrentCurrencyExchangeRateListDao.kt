package com.mvvm_clean.currency_exchange.core.source.disk.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mvvm_clean.currency_exchange.features.canada_facts.data.repo.CanadaFactsInfo
import com.mvvm_clean.currency_exchange.features.canada_facts.data.repo.tableName

@Dao
interface CurrentCurrencyExchangeRateListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCurrencyExchangeRates(currencyListInfo: CanadaFactsInfo)

    @Query("DELETE FROM $tableName")
    fun deleteAll()

    @Query("SELECT * FROM $tableName")
    fun getAllCurrencyExchangeRates():List<CanadaFactsInfo>

    @Query("SELECT * FROM $tableName WHERE id=:id")
    fun getCurrencyExchangeRateById(id: Int): CanadaFactsInfo

}