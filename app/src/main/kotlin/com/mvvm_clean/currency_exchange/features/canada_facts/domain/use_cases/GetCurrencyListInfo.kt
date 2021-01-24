package com.mvvm_clean.currency_exchange.features.canada_facts.domain.use_cases

import com.mvvm_clean.currency_exchange.core.domain.interactor.UseCase
import com.mvvm_clean.currency_exchange.features.canada_facts.data.repo.CurrencyListInfo
import com.mvvm_clean.currency_exchange.features.canada_facts.domain.repo.AboutCanadaRepository
import javax.inject.Inject

class GetCurrencyListInfo
@Inject constructor(
    private val aboutCanadaRepository: AboutCanadaRepository
    ) :UseCase<CurrencyListInfo, String>() {

    override suspend fun run(params: String) =
            aboutCanadaRepository.getCurrencyList( params )

}
