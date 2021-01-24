package com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.domain.repo

import com.mvvm_clean.currency_exchange.BuildConfig
import com.mvvm_clean.currency_exchange.core.data.NetworkHandler
import com.mvvm_clean.currency_exchange.core.domain.exception.Failure
import com.mvvm_clean.currency_exchange.core.domain.exception.Failure.NetworkConnection
import com.mvvm_clean.currency_exchange.core.domain.exception.Failure.ServerError
import com.mvvm_clean.currency_exchange.core.domain.extension.empty
import com.mvvm_clean.currency_exchange.core.domain.functional.Either
import com.mvvm_clean.currency_exchange.core.domain.functional.Either.Left
import com.mvvm_clean.currency_exchange.core.domain.functional.Either.Right
import com.mvvm_clean.currency_exchange.core.source.disk.DiskDataSource
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.CanadaFactsResponseEntity
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.CurrencyExchangeRequestEntity
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.CurrencyListResponseEntity
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.repo.CurrencyRateInfo
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data.repo.CurrencyListInfo
import com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.domain.api.AboutCanadaApiImpl
import retrofit2.Call
import javax.inject.Inject

/**
 * Api flow is controlled here
 */
interface AboutCanadaRepository {

    fun getFacts(
        currencyExchangeRequestEntity: CurrencyExchangeRequestEntity,
    ): Either<Failure, CurrencyRateInfo>

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val diskDataSource: DiskDataSource,
        private val apiImpl: AboutCanadaApiImpl
    ) : AboutCanadaRepository {

        override fun getFacts(
            currencyExchangeRequestEntity: CurrencyExchangeRequestEntity,
        ): Either<Failure, CurrencyRateInfo> {

            val currencyExchangeRequestEntityDb =
                diskDataSource.getCurrencyExchangeRateById(currencyExchangeRequestEntity.id)

//            if ( currencyExchangeRequestEntityDb != null){
//                return Right(currencyExchangeRequestEntityDb)
//            }else{
            when (networkHandler.isNetworkAvailable()) {
                true ->
                    return request(
                        apiImpl.getFacts(
                            currencyExchangeRequestEntity.accessKey,
                            currencyExchangeRequestEntity.currency,
                            currencyExchangeRequestEntity.source,
                            currencyExchangeRequestEntity.format
                        ),
                        {
                            it.toFacts()

                        },
                        CanadaFactsResponseEntity(
                            String.empty(),
                            String.empty(),
                            String.empty(),
                            Long.MIN_VALUE,
                            String.empty(),
                            emptyMap(), null
                        )
                    )
                false -> return Left(NetworkConnection)
            }
//            }
        }

        override fun getCurrencyList(
            accessKey: String
        ): Either<Failure, CurrencyListInfo> {


            return when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    apiImpl.getCurrencyList(accessKey),
                    { it.toFacts() },
                    CurrencyListResponseEntity(
                        String.empty(),
                        String.empty(),
                        String.empty(),
                        emptyMap()
                    )
                )
                false -> Left(NetworkConnection)
            }
        }


        private fun <T, R> request(
            call: Call<T>,
            transform: (T) -> R,
            default: T
        ): Either<Failure, R> {

            return try {
                val response = call.execute()
                when (response.isSuccessful) {
                    true -> {
                        Right(transform((response.body() ?: default)))
                    }
                    false -> {
                        ServerError.message = "sdf"
                        return Left(ServerError)
                    }

                }
            } catch (exception: Throwable) {

                if (BuildConfig.DEBUG)
                    exception.printStackTrace()

                Left(ServerError)
            }
        }
    }

    fun getCurrencyList(accessKey: String): Either<Failure, CurrencyListInfo>
}
