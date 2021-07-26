package com.mordeniuss.moviedbclient.mvp.model.datasource

import com.mordeniuss.moviedbclient.mvp.model.entity.Movie
import com.mordeniuss.moviedbclient.mvp.model.entity.room.RoomMovie
import com.mordeniuss.moviedbclient.mvp.model.room.dao.MovieDao
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.joda.time.DateTime

class MovieLocalDataSource(val dao: MovieDao) {
    fun save(movie: Movie): Completable = Completable.fromAction {
        dao.insert(mapToRoom(movie))
    }.subscribeOn(Schedulers.io())

    fun getById(id: Long): Single<Movie> = Single.fromCallable {
        val roomMovie = dao.getById(id)
        roomMovie?.let {
            return@fromCallable mapFromRoom(it)
        } ?: throw Exception()
    }.subscribeOn(Schedulers.io())

    private fun mapToRoom(movie: Movie) =
        RoomMovie(movie.id, movie.title, movie.description, movie.poster, movie.date?.millis, movie.isLiked)

    private fun mapFromRoom(movie: RoomMovie) =
        Movie(movie.id, movie.title, movie.description, movie.poster, movie.date?.let {DateTime(it)}, movie.isLiked)
}