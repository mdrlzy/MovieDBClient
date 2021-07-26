package com.mordeniuss.moviedbclient.mvp.presenter

import com.mordeniuss.moviedbclient.mvp.model.entity.Movie
import com.mordeniuss.moviedbclient.mvp.model.repo.MovieRepo
import com.mordeniuss.moviedbclient.mvp.presenter.list.IMovieListPresenter
import com.mordeniuss.moviedbclient.mvp.view.SearchView
import com.mordeniuss.moviedbclient.mvp.view.item.MovieItemView
import com.mordeniuss.moviedbclient.ui.fragment.SearchFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class SearchPresenter: MvpPresenter<SearchView>() {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var movieRepo: MovieRepo

    val listPresenter = MovieListPresenter()

    inner class MovieListPresenter: IMovieListPresenter {
        val movies = mutableListOf<Movie>()

        override fun getCount() = movies.size

        override fun bindView(view: MovieItemView) {
            val movie = movies[view.pos]
            view.setTitle(movie.title)
            view.setDescription(movie.description)
            movie.dateFormatted()?.let { view.setDate(it) }
            view.setLiked(movie.isLiked)
            movie.poster?.let { view.setPoster(it) }
        }

        override fun onLikeBtnClicked(view: MovieItemView) {
            val movie = movies[view.pos]
            movieRepo.like(movie).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    view.setLiked(movie.isLiked)
                },
                {}
            )
        }
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }

    fun onSearchTextChanged(query: String) {
        if (query.isEmpty())
            return
        handleLoadingState()
        search(query)
    }

    fun onRefresh(query: String) {
        if (query.isEmpty())
            return
        viewState.setContentState(SearchFragment.ContentState.REFRESHING)
        search(query)
    }

    private fun search(query: String) {
        movieRepo.search(query, 1).observeOn(AndroidSchedulers.mainThread()).subscribe(
            {
                listPresenter.movies.clear()
                listPresenter.movies.addAll(it.results)
                handleNotFoundState(query)
                viewState.updateAdapter()
            },
            { e ->
                e.printStackTrace()
                handleErrorQueryState()
                viewState.showNoInternetSnackBar()
            }
        )
    }

    private fun handleLoadingState() {
        if (listPresenter.movies.isEmpty()) {
            viewState.setContentState(SearchFragment.ContentState.LOADING)
        } else
            viewState.setContentState(SearchFragment.ContentState.ITEMS_AND_LOADING)
    }

    private fun handleNotFoundState(query: String) {
        if (listPresenter.movies.isEmpty()) {
            viewState.setContentState(SearchFragment.ContentState.NOT_FOUND)
            viewState.setNotFoundQuery(query)
        } else
            viewState.setContentState(SearchFragment.ContentState.ITEMS)
    }

    private fun handleErrorQueryState() {
        if (listPresenter.movies.isEmpty())
            viewState.setContentState(SearchFragment.ContentState.ERROR_QUERY)
        else
            viewState.setContentState(SearchFragment.ContentState.ITEMS)
    }

}