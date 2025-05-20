package com.example.movies.data.repository

import com.example.movies.BuildConfig
import com.example.movies.data.api.MovieApi
import com.example.movies.data.mapper.MovieResponseToEntityMapper
import com.example.movies.domain.IMovieRepository
import com.example.movies.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(
    private val api: MovieApi,
    private val mapper: MovieResponseToEntityMapper
) : IMovieRepository {

    override suspend fun getMovie(type: String, contentStatus: String): List<Movie> {
        return withContext(Dispatchers.IO) {
            mapper.mapMovie(
                api.getMovies(
                    apiKey = BuildConfig.MOVIE_API_KEY,
                    page = 1,
                    limit = 15,
                    selectFields = listOf(
                        "id", "name", "description", "year", "rating",
                        "movieLength", "genres", "countries", "poster", "persons"
                    ),
                    notNullFields = listOf(
                        "id", "name", "description", "year", "movieLength", "poster.url", "persons.name"
                    ),
                    sortField = listOf("rating.imdb"),
                    sortType = "-1",
                    type = listOf(type),
                    status= listOf(contentStatus),
                    //year = "2002"
                )
            )
        }
    }
}
