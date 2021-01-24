package com.mvvm_clean.currency_exchange.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.mvvm_clean.currency_exchange.AboutCanadaApplication
import com.mvvm_clean.currency_exchange.core.di.ApplicationComponent
import com.mvvm_clean.currency_exchange.core.domain.extension.viewContainer
import kotlinx.android.synthetic.main.activity_layout.*
import javax.inject.Inject

/**
 * Base Fragment class with helper methods for handling views and back button events.
 *
 * @see Fragment
 */
abstract class BaseFragment : Fragment() {

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as AboutCanadaApplication).appComponent
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    abstract fun layoutId(): Int

    // Override Methods
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(layoutId(), container, false)

    //---

    open fun onBackPressed() {}


    internal fun showProgress() = progressStatus(View.VISIBLE)

    internal fun hideProgress() = progressStatus(View.GONE)

    private fun progressStatus(viewStatus: Int) =
        with(activity) { if (this is BaseActivity) this.pb_fact_list.visibility = viewStatus }

    internal fun notifyWithAction(message: String) {
        val snackBar = Snackbar.make(viewContainer, message, Snackbar.LENGTH_SHORT)
        snackBar.show()
    }
}
