package com.example.movies.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.movies.model.Movie


@Composable
fun ReviewMovieScreen(movie: Movie, navController: NavController) {

    if (movie != null) {
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start
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
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(movie.posterUrl),
                        contentDescription = null,
                        modifier = Modifier.size(400.dp)
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${movie.premiere} г., ${movie.genre.joinToString(", ")}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.DarkGray
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "${movie.countries.joinToString(", ")}, ${movie.duration} мин.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.DarkGray
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Описание фильма \"${movie.title}\"", fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = movie.description,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(24.dp))

                if (movie.director.isNotEmpty()) {
                    androidx.compose.material3.Text(
                        text = "Режиссер: ${movie.director.joinToString(", ")}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                if (movie.starring.isNotEmpty()) {
                    androidx.compose.material3.Text(
                        text = "В ролях: ",
                        style = MaterialTheme.typography.bodyLarge,
                    )

                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        movie.starring.forEach { actor ->
                            Text(
                                text = actor,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier
                                    .padding(vertical = 4.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

        }
    } else {
        Text(text = "Фильм не найден", style = MaterialTheme.typography.bodyMedium)
    }
}





