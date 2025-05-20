package com.example.movies.screens

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.movies.model.MovieViewModel


@Composable
fun ListScreen(viewModel: MovieViewModel, onMovieClick: (Long) -> Unit) {
    val state = viewModel.viewState
    val isConnected = checkInternetConnection(LocalContext.current)

    if (!isConnected) {
        NoInternetScreen { viewModel.loadMovies() }
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.items) { movie ->
                ListItem(
                    modifier = Modifier
                        .clickable { onMovieClick(movie.id) }
                        .padding(8.dp),
                    headlineContent = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(movie.posterUrl),
                                contentDescription = null,
                                modifier = Modifier.size(150.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(movie.title, style = MaterialTheme.typography.titleLarge)
                                Text(
                                    movie.description.take(100) + "...",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun NoInternetScreen(onRetryClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Отсутствует интернет-соединение", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetryClick) {
            Text(text = "обновить")
        }
    }
}

@Composable
fun checkInternetConnection(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetwork ?: return false
    val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
    return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}