package com.mvvm_clean.currency_exchange.features.canada_facts.domain.use_cases

import com.mvvm_clean.currency_exchange.core.domain.interactor.UseCase
import com.mvvm_clean.currency_exchange.features.canada_facts.data.CurrencyExchangeRequestEntity
import com.mvvm_clean.currency_exchange.features.canada_facts.data.repo.CurrencyRateInfo
import com.mvvm_clean.currency_exchange.features.canada_facts.domain.repo.AboutCanadaRepository
import javax.inject.Inject

class GetCanadaFactsInfo
@Inject constructor(private val aboutCanadaRepository: AboutCanadaRepository) :
    UseCase<CurrencyRateInfo, CurrencyExchangeRequestEntity>() {

    override suspend fun run(params: CurrencyExchangeRequestEntity) =
        aboutCanadaRepository.getFacts(
            params
        )

}
