package com.example.movies.screens

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.example.movies.data.model.MovieEntity
import com.example.movies.model.FavoriteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(navController: NavHostController) {
    val favoriteViewModel: FavoriteViewModel = viewModel()

    LaunchedEffect(key1 = true) {
        favoriteViewModel.loadFavoriteMovie()
    }

    val movieEntities = favoriteViewModel.favoriteMovieList

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF89224)
                ),
                modifier = Modifier.height(70.dp),
                title = {
                    Text(
                        text = "Избранные фильмы",
                        color = Color.Black,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn {
                items(movieEntities, key = { it.id }) { movie ->
                    MovieItem(
                        movieEntity = movie,
                        onClick = { navController.navigate("movie_detail/${movie.id}") },
                        onRemoveClick = { favoriteViewModel.removeMovieFromFavorite(movie) }
                    )
                }
            }
        }
    }
}

@Composable
fun MovieItem(
    movieEntity: MovieEntity,
    onClick: () -> Unit,
    onRemoveClick: () -> Unit
) {
    val title = movieEntity.title
    val imageBytes = movieEntity.imageBytes
    val imageBitmap = imageBytes?.let {
        BitmapFactory.decodeByteArray(it, 0, it.size)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            if (imageBitmap != null) {
                Image(
                    bitmap = imageBitmap.asImageBitmap(),
                    contentDescription = title,
                    modifier = Modifier
                        .size(140.dp)
                        .padding(end = 16.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    imageVector = Icons.Filled.AccountBox,
                    contentDescription = "Default Image",
                    modifier = Modifier
                        .size(140.dp)
                        .padding(end = 16.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Год: ${movieEntity.year}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Жанр: ${movieEntity.genres}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Страна: ${movieEntity.country}", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = movieEntity.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            IconButton(
                onClick = onRemoveClick,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Remove from favorites",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
