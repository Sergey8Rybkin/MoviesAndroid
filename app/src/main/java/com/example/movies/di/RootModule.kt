package com.example.movies.di

import com.example.movies.data.mapper.MovieResponseToEntityMapper
import com.example.movies.data.repository.MovieRepository
import com.example.movies.domain.IMovieRepository
import com.example.movies.model.MovieViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val rootModule = module {
    single<IMovieRepository> { MovieRepository(get(), get()) }
    factory { MovieResponseToEntityMapper() }
    viewModel { MovieViewModel(get(), get()) }
}