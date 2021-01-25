package com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.domain.use_cases

import com.mvvm_clean.currency_exchange.core.domain.interactor.UseCase
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.models.CurrencyExchangeRequestEntity
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.domain.models.CurrencyRateInfo
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.domain.repo.AboutCanadaRepository
import javax.inject.Inject

class GetCurrencyRateInfo
@Inject constructor(private val aboutCanadaRepository: AboutCanadaRepository) :
    UseCase<CurrencyRateInfo, CurrencyExchangeRequestEntity>() {

    override suspend fun run(params: CurrencyExchangeRequestEntity) =
        aboutCanadaRepository.getFacts(
            params
        )

}
