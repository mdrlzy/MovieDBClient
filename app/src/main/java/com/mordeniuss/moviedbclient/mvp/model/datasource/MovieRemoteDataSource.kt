package com.mordeniuss.moviedbclient.mvp.model.datasource

import com.mordeniuss.moviedbclient.mvp.model.api.MovieDBApi
import com.mordeniuss.moviedbclient.mvp.model.entity.Movie
import com.mordeniuss.moviedbclient.mvp.model.entity.Pagination
import com.mordeniuss.moviedbclient.mvp.model.entity.api.ResponseMovie
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieRemoteDataSource(
    val api: MovieDBApi
) {
    fun search(query: String, page: Int = 1): Single<Pagination<Movie>> = api.searchRepositories(query, page).flatMap { respPag ->
        val results = respPag.results.map { mapMovieFromApi(it) }
        Single.just(Pagination(page, results))
    }.subscribeOn(Schedulers.io())

    private fun mapMovieFromApi(movie: ResponseMovie): Movie {
        return Movie(movie.id, movie.title, movie.overview, movie.resolvePosterUrl(), movie.resolveDate(), false)
    }
}