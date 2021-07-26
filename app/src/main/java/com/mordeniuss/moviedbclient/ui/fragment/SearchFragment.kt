package com.mordeniuss.moviedbclient.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.widget.textChanges
import com.mordeniuss.moviedbclient.R
import com.mordeniuss.moviedbclient.mvp.presenter.SearchPresenter
import com.mordeniuss.moviedbclient.mvp.view.SearchView
import com.mordeniuss.moviedbclient.ui.App
import com.mordeniuss.moviedbclient.ui.adapter.MovieRVAdapter
import com.mordeniuss.moviedbclient.utils.gone
import com.mordeniuss.moviedbclient.utils.visible
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.layout_error_query.*
import kotlinx.android.synthetic.main.layout_not_found.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import java.util.concurrent.TimeUnit

class SearchFragment : MvpAppCompatFragment(), SearchView {
    enum class ContentState {
        ITEMS, LOADING, ITEMS_AND_LOADING, ERROR_QUERY, NOT_FOUND, REFRESHING
    }

    private val presenter by moxyPresenter {
        SearchPresenter().apply { App.instance.appComponent.inject(this) }
    }

    var adapter: MovieRVAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.instance.appComponent.inject(this)
    }

    override fun init() {

        et_search.textChanges()
//            .skipInitialValue()
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    //todo: called before text change
                    presenter.onSearchTextChanged(it.toString()) },
                {})

        layout_refresh.setOnRefreshListener {
            presenter.onRefresh(et_search.text.toString())
        }
        iv_refresh.setOnClickListener {
            presenter.onRefresh(et_search.text.toString())
        }

        adapter = MovieRVAdapter(presenter.listPresenter)
        adapter!!.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        rv_movies.layoutManager = LinearLayoutManager(context)
        rv_movies.adapter = adapter
    }

    override fun setContentState(state: ContentState) {
        when(state) {
            ContentState.ITEMS -> {
                rv_movies.visible()
                progress_linear.gone()
                progress_circular.gone()
                layout_refresh.isEnabled = true
                layout_refresh.isRefreshing = false
                layout_not_found.gone()
                layout_error_query.gone()
            }
            ContentState.ITEMS_AND_LOADING -> {
                rv_movies.visible()
                progress_linear.visible()
                progress_circular.gone()
                layout_refresh.isEnabled = false
                layout_refresh.isRefreshing = false
                layout_not_found.gone()
                layout_error_query.gone()
            }
            ContentState.LOADING -> {
                rv_movies.gone()
                progress_linear.gone()
                progress_circular.visible()
                layout_refresh.isEnabled = false
                layout_refresh.isRefreshing = false
                layout_not_found.gone()
                layout_error_query.gone()
            }
            ContentState.ERROR_QUERY -> {
                rv_movies.gone()
                progress_linear.gone()
                progress_circular.gone()
                layout_refresh.isEnabled = false
                layout_refresh.isRefreshing = false
                layout_not_found.gone()
                layout_error_query.visible()
            }
            ContentState.NOT_FOUND -> {
                rv_movies.gone()
                progress_linear.gone()
                progress_circular.gone()
                layout_refresh.isEnabled = false
                layout_refresh.isRefreshing = false
                layout_not_found.visible()
                layout_error_query.gone()
            }
            ContentState.REFRESHING -> {
                rv_movies.visible()
                progress_linear.gone()
                progress_circular.gone()
                layout_refresh.isEnabled = true
                layout_refresh.isRefreshing = true
                layout_not_found.gone()
                layout_error_query.gone()
            }
        }
    }


    override fun showNoInternetSnackBar() {
        Snackbar.make(
            requireView(),
            "Проверьте соединение с интернетом и попробуйте еще раз",
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun setNotFoundQuery(query: String) {
        tv_not_found.text = resources.getString(R.string.not_found_template, query)
    }

    override fun updateAdapter() {
        adapter?.notifyDataSetChanged()
    }
}