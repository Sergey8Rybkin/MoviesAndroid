package com.example.movies.model

data class MovieDisplayModel(
    val premiereText: String,
    val genreText: String,
    val ratingText: String,
    val durationText: String,
    val descriptionText: String,
    val countriesText: String,
    val directorText: String?,
    val starringText: List<String>
) {
    companion object {
        fun fromMovie(movie: Movie): MovieDisplayModel {
            return MovieDisplayModel(
                premiereText = "${movie.premiere} г.",
                genreText = movie.genre.joinToString(", "),
                ratingText = "Рейтинг: ${movie.rating}/10",
                durationText = "${movie.countries.joinToString(", ")}, ${movie.duration} мин.",
                descriptionText = movie.description ?: "Описание отсутствует",
                countriesText = movie.countries.joinToString(", "),
                directorText = if (movie.director.isNotEmpty()) "Режиссер: ${movie.director.joinToString(", ")}" else null,
                starringText = movie.starring
            )
        }
    }
}
