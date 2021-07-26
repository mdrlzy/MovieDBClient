package com.mordeniuss.moviedbclient.mvp.view

import com.mordeniuss.moviedbclient.ui.fragment.SearchFragment
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface SearchView: MvpView {
    fun init()
    fun updateAdapter()
    fun setNotFoundQuery(query: String)
    fun setContentState(state: SearchFragment.ContentState)

    @StateStrategyType(SkipStrategy::class)
    fun showNoInternetSnackBar()
}