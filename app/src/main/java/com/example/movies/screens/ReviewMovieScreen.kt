package com.example.movies.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.movies.model.MovieViewModel


@Composable
fun ReviewMovieScreen(movieId: Int, navController: NavController, viewModel: MovieViewModel = viewModel()) {
    val movie = viewModel.movies.find { it.id == movieId }

    if (movie != null) {
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start // Выравнивание элементов по левому краю
        ) {
            item {
                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFF89224),
                    ),
                    modifier = Modifier
                ) {
                    Text(
                        text = "Назад",
                        color = Color.Black
                    )
                }
            }
            item {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.fillMaxWidth() // Занимает всю ширину
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center // Центрирование содержимого в Box
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(movie.posterUrl),
                        contentDescription = null,
                        modifier = Modifier.size(400.dp) // Размер изображения
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
            item {
                Text(text = "Описание: " + movie.description, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Премьера: " + movie.premiere, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Страна: " + movie.country, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Жанр: " + movie.genre, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Режиссер: " + movie.director, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "В ролях: " + movie.starring, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Продолжительность: " + movie.duration, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Возраст: " + movie.age, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Рейтинг MPAA: " + movie.mpaa, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(24.dp))
            }

        }
    } else {
        Text(text = "Фильм не найден", style = MaterialTheme.typography.bodyMedium)
    }
}





