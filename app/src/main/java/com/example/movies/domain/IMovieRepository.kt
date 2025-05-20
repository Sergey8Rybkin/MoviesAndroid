package com.example.movies.domain

import com.example.movies.model.Movie

interface IMovieRepository {
    suspend fun getMovie(type: String, contentStatus: String, page: Int, pageSize: Int): List<Movie>
}