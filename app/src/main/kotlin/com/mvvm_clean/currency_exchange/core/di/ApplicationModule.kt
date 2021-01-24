package com.mvvm_clean.currency_exchange.core.di

import com.mvvm_clean.currency_exchange.App
import com.mvvm_clean.currency_exchange.BuildConfig
import com.mvvm_clean.currency_exchange.features.canada_facts.domain.repo.AboutCanadaRepository
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: App) {
    var timeout = 5 * 10L

    @Provides
    @Singleton
    fun provideApplicationContext(): App = application

    @Provides
    @Singleton
    fun provideContext() =
        application.applicationContext

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(createClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }

        okHttpClientBuilder.addInterceptor(Interceptor { chain: Interceptor.Chain ->
            var request: Request? = null
            try {
                val original = chain.request()
                request = original.newBuilder().build()
                return@Interceptor chain.proceed(request)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            chain.proceed(request!!)
        })

        okHttpClientBuilder.readTimeout(timeout, TimeUnit.SECONDS)
        okHttpClientBuilder.connectTimeout(timeout, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(timeout, TimeUnit.SECONDS)

        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideCanadaFactsRepository(dataSource: AboutCanadaRepository.Network): AboutCanadaRepository =
        dataSource

}
