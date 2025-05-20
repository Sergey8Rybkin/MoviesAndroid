package com.example.movies.state

import androidx.compose.runtime.Stable
import com.example.movies.model.Movie
import com.example.movies.presentation.model.MovieUiModel

@Stable
interface ListState {
    val searchName: String
    val filterContentStatus: String
    val items: List<MovieUiModel>
    val error: String?
    var loading: Boolean
}