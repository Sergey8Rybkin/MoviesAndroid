package com.example.movies.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class MovieEntity(
    @PrimaryKey val id: String = "",
    val title: String = "",
    val genres: String = "",
    val imageUrl: String = "",
    val year: String = 0.toString(),
    val country: String = "",
    val description: String = "",
    val imageBytes: ByteArray? = null
)