package com.mordeniuss.moviedbclient.mvp.model.repo

import com.mordeniuss.moviedbclient.mvp.model.datasource.MovieLocalDataSource
import com.mordeniuss.moviedbclient.mvp.model.datasource.MovieRemoteDataSource
import com.mordeniuss.moviedbclient.mvp.model.entity.Movie
import com.mordeniuss.moviedbclient.mvp.model.entity.Pagination
import com.mordeniuss.moviedbclient.mvp.model.entity.exception.NoInternetException
import com.mordeniuss.moviedbclient.mvp.model.network.NetworkStatus
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieRepo(
    val local: MovieLocalDataSource,
    val remote: MovieRemoteDataSource,
    val networkStatus: NetworkStatus
) {
    fun search(query: String, page: Int): Single<Pagination<Movie>> =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {
                remote.search(query, page).flatMap { pag ->
                    checkLocalMoviesZip(pag)
                }
            } else
                Single.error(NoInternetException())
        }.subscribeOn(Schedulers.io())

    fun like(movie: Movie): Completable = Completable.fromAction {
        movie.isLiked = true
        local.save(movie).subscribe()
    }.subscribeOn(Schedulers.io())

    private fun checkLocalMoviesZip(pag: Pagination<Movie>): Single<Pagination<Movie>> {
        if (pag.results.isEmpty())
            return Single.just(pag)

        val localSingles = mutableListOf<Single<Movie>>()

        pag.results.forEach { remoteMovie ->
            val single = local.getById(remoteMovie.id).onErrorReturn {remoteMovie}.flatMap { localMovie ->
                remoteMovie.isLiked = localMovie.isLiked
                Single.just(remoteMovie)
            }
            localSingles.add(single)
        }

        return Single.zip(localSingles, Function<Array<Any>, Pagination<Movie>> { list ->
            val movies = list.map { it as Movie }
            pag.results = movies
            pag
        })
    }
}