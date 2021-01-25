package com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.presentation.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mvvm_clean.currency_exchange.R
import com.mvvm_clean.currency_exchange.core.domain.extension.getString
import com.mvvm_clean.currency_exchange.core.domain.extension.inflate
import kotlinx.android.synthetic.main.currency_rate_list_items.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

// Adapter responsible to show item of canada fact list
class CurrencyRateListAdapter
@Inject constructor() : RecyclerView.Adapter<CurrencyRateListAdapter.ViewHolder>() {

    private lateinit var collectionKeys: ArrayList<String>
    internal var collection: Map<String, Double> by Delegates.observable(emptyMap()) { _, _, _ ->
        collectionKeys = ArrayList(collection.keys)
        notifyDataSetChanged()
    }

    // Override Methods
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.currency_rate_list_items))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(collectionKeys[position])

    override fun getItemCount() = collection.size
    //---

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(factRowModel: String) {

            itemView.tv_list_item_heading.text = factRowModel +
                    itemView.getString(R.string.space) +
                    collection.get(factRowModel).toString()
        }
    }
}

