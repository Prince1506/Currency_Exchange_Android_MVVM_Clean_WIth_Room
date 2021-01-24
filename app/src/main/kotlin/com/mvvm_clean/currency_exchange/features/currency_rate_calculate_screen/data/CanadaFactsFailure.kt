package com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.data

import com.mvvm_clean.currency_exchange.core.domain.exception.Failure.FeatureFailure

/**
 * Data to be shown when canada facts API fail
 */
class CanadaFactsFailure {
    class ListNotAvailable : FeatureFailure()
}
