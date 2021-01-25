package com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.domain.use_cases

import com.mvvm_clean.currency_exchange.core.domain.interactor.UseCase
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.models.CurrencyListInfo
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.domain.repo.CurrencyRateRepository
import javax.inject.Inject

class GetCurrencyListInfo
@Inject constructor(
    private val currencyRateRepository: CurrencyRateRepository
) : UseCase<CurrencyListInfo, String>() {

    override suspend fun run(params: String) =
        currencyRateRepository.getCurrencyList(params)

}
