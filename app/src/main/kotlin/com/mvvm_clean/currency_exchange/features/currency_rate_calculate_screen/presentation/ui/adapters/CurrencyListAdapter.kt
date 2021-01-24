package com.mvvm_clean.currency_exchange.features.currency_rate_calculate_screen.presentation.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mvvm_clean.currency_exchange.R
import com.mvvm_clean.currency_exchange.core.domain.extension.inflate
import com.mvvm_clean.currency_exchange.core.presentation.navigation.Navigator
import kotlinx.android.synthetic.main.currency_list_item.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class CurrencyListAdapter
@Inject constructor() : RecyclerView.Adapter<CurrencyListAdapter.ViewHolder>() {


    private lateinit var collectionKeys: ArrayList<String>

    internal var collection: Map<String, String> by Delegates.observable(emptyMap()) { _, _, _ ->
        collectionKeys = ArrayList(collection.keys)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.currency_list_item))

    override fun onBindViewHolder(viewHolder: CurrencyListAdapter.ViewHolder, position: Int) =
        viewHolder.bind(collectionKeys[position].toString(), clickListener)

    override fun getItemCount() = collectionKeys.size

    internal var clickListener: (String, Navigator.Extras) -> Unit = { _, _ -> }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(currency: String, clickListener: (String, Navigator.Extras) -> Unit) {
            itemView.tv_currency_name.text = currency
            itemView.setOnClickListener {
                clickListener(currency, Navigator.Extras(itemView.tv_currency_name))
            }
        }
    }
}
