package com.example.movies.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.movies.model.Movie
import com.example.movies.model.MovieViewModel

@Composable
fun ListScreen(onMovieClick: (Int) -> Unit) {
    val viewModel: MovieViewModel = viewModel()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(viewModel.movies) { movie ->
            MovieListItem(movie = movie, onClick = { onMovieClick(movie.id) })
        }
    }
}

// Вынесем отображение элемента списка в отдельную функцию
@Composable
fun MovieListItem(movie: Movie, onClick: () -> Unit) {
    ListItem(
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp),
        headlineContent = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top // Выравнивание по верхней части
            ) {
                Image(
                    painter = rememberAsyncImagePainter(movie.posterUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(movie.title, style = MaterialTheme.typography.titleLarge)
                    Text(movie.description, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    )
}
