package com.mvvm_clean.currency_exchange.features.canada_facts.presentation.models

import android.os.Parcelable
import com.mvvm_clean.currency_exchange.core.domain.extension.isEmptyOrNull
import kotlinx.android.parcel.Parcelize

// Part of canada fact list view model to be shown on UI
@Parcelize
data class FactRowModel(
    val title: String? = null,
    val description: String? = null,
    val imageHref: String? = null
) : Parcelable {

    val titleNotNull: String?
        get() = if (String.isEmptyOrNull(title)) "-" else title

    val descriptionNotNull: String?
        get() = if (String.isEmptyOrNull(description)) "-" else description

    val imageHrefNotNull: String?
        get() = if (String.isEmptyOrNull(imageHref)) "-" else imageHref

    fun isEmpty() =
        String.isEmptyOrNull(title) &&
                String.isEmptyOrNull(description) &&
                String.isEmptyOrNull(imageHref)
}