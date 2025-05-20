package com.example.movies.model

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.datastore.DataStoreManager
import com.example.movies.domain.IMovieRepository
import com.example.movies.state.ListState
import com.example.movies.utils.LocalUtils.isFilter
import com.example.movies.utils.launchLoadingAndError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.logging.Logger
import java.net.UnknownHostException

class MovieViewModel(
    val context: Context,
    private val repository: IMovieRepository
) : ViewModel() {
    private val mutableState = MutableListState()
    val viewState = mutableState as ListState
    val dataStoreManager = DataStoreManager(context)

    init {
        loadTmp()
    }
    private fun loadTmp() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                dataStoreManager.getSettings().collect { settings ->

                    val savedStatus = settings.type.split(",").filter { it.isNotBlank() }
                    val savedContentRating =
                        settings.status.split(",").filter { it.isNotBlank() }

                    isFilter.value =
                        savedStatus.isNotEmpty() || savedContentRating.isNotEmpty()
                    if (isFilter.value) {
                        loadMovies(savedStatus[0], savedContentRating[0])
                    }else{
                        loadMovies(mutableState.searchName, mutableState.filterContentStatus )
                    }
                }
            } catch (e: Exception) {
            }
        }
    }

    fun loadMovies(type: String, contentStatus: String) {
        LOG.info("loadFilms, $type, $contentStatus")
        viewModelScope.launchLoadingAndError(
            handleError = { error ->
                mutableState.error = when (error) {
                    is IOException -> "Проверьте подключение к интернету."
                    else -> error.localizedMessage
                }},
            updateLoading = { mutableState.loading = it }
        ) {
            mutableState.error = null
            mutableState.items = repository.getMovie(type, contentStatus)
        }
    }

    fun getMovieById(id: Long): Movie? {
        return viewState.items.find { it.id == id }
    }

    private class MutableListState : ListState {
        override var searchName: String by mutableStateOf(DEFAULT_SEARCH_NAME)
        override var filterContentStatus: String by mutableStateOf(DEFAULT_CONTENT_STATUS)
        override var items: List<Movie> by mutableStateOf(emptyList())
        override var error: String? by mutableStateOf(null)
        override var loading: Boolean by mutableStateOf(false)
    }

    companion object {
        private const val DEFAULT_CONTENT_STATUS = "completed"
        private const val DEFAULT_SEARCH_NAME = "movie"
        private val LOG = Logger.getLogger(MovieViewModel::class.java.name)
    }
}
