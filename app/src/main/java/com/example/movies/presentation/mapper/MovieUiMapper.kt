package com.example.movies.presentation.mapper

import com.example.movies.model.Movie
import com.example.movies.presentation.model.MovieUiModel

class MovieUiMapper {
    fun mapMovie(entity: Movie): MovieUiModel {
        return MovieUiModel(
            entity.id,
            entity.title,
            entity.description,
            entity.posterUrl,
            "${entity.premiere} г.",
            entity.type,
            entity.countries.joinToString(", "),
            entity.genre.joinToString(", "),
            entity.director.joinToString(", "),
            entity.starring,
            "${entity.duration} мин.",
            entity.rating,
        )
    }
}