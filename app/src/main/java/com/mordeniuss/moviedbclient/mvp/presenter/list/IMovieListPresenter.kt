package com.mordeniuss.moviedbclient.mvp.presenter.list

import com.mordeniuss.moviedbclient.mvp.view.item.MovieItemView

interface IMovieListPresenter {
    fun getCount(): Int
    fun bindView(view: MovieItemView)
    fun onLikeBtnClicked(view: MovieItemView)
}