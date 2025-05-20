package com.example.movies.utils

import com.example.movies.data.model.MovieEntity
import com.example.movies.presentation.model.MovieUiModel

object MovieEntityMapper {
    fun toEntity(movieUiModel: MovieUiModel): MovieEntity {
        return MovieEntity(
            id = movieUiModel.id.toString(),
            title = movieUiModel.title,
            genres = movieUiModel.genre,
            imageUrl = movieUiModel.posterUrl,
            year = movieUiModel.premiere.replace(" г.", "").toInt().toString(),
            country = movieUiModel.countries,
            description = movieUiModel.description
        )
    }
}