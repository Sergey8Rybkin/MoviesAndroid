package com.example.movies.di

import androidx.datastore.core.DataStore
import com.example.consecutivep.domain.model.ProfileEntity
import com.example.movies.data.mapper.MovieResponseToEntityMapper
import com.example.movies.data.repository.MovieRepository
import com.example.movies.domain.IMovieRepository
import com.example.movies.model.MovieViewModel
import com.example.movies.presentation.mapper.MovieUiMapper
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import com.example.movies.data.datastore.DataStoreManager
import com.example.movies.data.datastore.SourceProvider
import com.example.movies.data.repository.ProfileRepository
import com.example.movies.domain.IProfileRepository
import com.example.movies.model.EditProfileViewModel
import com.example.movies.model.ProfileViewModel
import org.koin.core.qualifier.named


val rootModule = module {
    single<IMovieRepository> { MovieRepository(get(), get()) }
    factory { MovieResponseToEntityMapper() }
    factory { MovieUiMapper() }
    factory<DataStore<ProfileEntity>>(named("profile")) { SourceProvider(get()).provide() }
    single<IProfileRepository> { ProfileRepository() }

    single { DataStoreManager(get()) }
    viewModel { MovieViewModel(get(), get(), get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { EditProfileViewModel(get()) }
}