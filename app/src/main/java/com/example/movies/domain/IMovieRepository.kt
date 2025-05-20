package com.example.movies.domain

import com.example.movies.model.Movie

interface IMovieRepository {
    suspend fun getMovie(nameSearch: String): List<Movie>
}