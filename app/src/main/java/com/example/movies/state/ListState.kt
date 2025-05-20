package com.example.movies.state

import androidx.compose.runtime.Stable
import com.example.movies.model.Movie

@Stable
interface ListState {
    val searchName: String
    val items: List<Movie>
    val error: String?
    var loading: Boolean
}