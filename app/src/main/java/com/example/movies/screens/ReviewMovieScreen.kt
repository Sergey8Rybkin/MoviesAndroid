package com.example.movies.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.movies.presentation.model.MovieUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewMovieScreen(movie: MovieUiModel, navController: NavController) {
    if (movie == null) {
        Text(
            text = "Информация о фильме недоступна",
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
            color = Color.Red,
            style = MaterialTheme.typography.bodyLarge
        )
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF89224)
                ),
                modifier = Modifier.height(70.dp),
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = movie.title,
                            color = Color.Black,
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                },
                navigationIcon = {
                    Row(
                        modifier = Modifier
                            .fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Назад",
                                tint = Color.Black,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start
        ) {
            item {
                Image(
                    painter = rememberAsyncImagePainter(movie.posterUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(400.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "${movie.premiere}, ${movie.genre}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = movie.duration,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.DarkGray
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
            if(movie.description.isNotEmpty()){
                item {
                    Text(
                        text = "Описание фильма \"${movie.title}\"",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = movie.description,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
            item {
                movie.director?.let { director ->
                    Text(
                        text = director,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                if (movie.starring.isNotEmpty()) {
                    Text(
                        text = "В ролях:",
                        style = MaterialTheme.typography.bodyLarge,
                    )

                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        movie.starring.forEach { actor ->
                            Text(
                                text = actor,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
