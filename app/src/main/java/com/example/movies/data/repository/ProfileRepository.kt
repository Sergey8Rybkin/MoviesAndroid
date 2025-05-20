package com.example.movies.data.repository

import androidx.datastore.core.DataStore
import com.example.consecutivep.domain.model.ProfileEntity
import com.example.movies.domain.IProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject


class ProfileRepository: IProfileRepository {
    private val dataStore : DataStore<ProfileEntity> by inject(DataStore::class.java, named("profile"))

    override suspend fun observeProfile(): Flow<ProfileEntity> = dataStore.data

    override suspend fun getProfile(): ProfileEntity? = dataStore.data.firstOrNull()

    override suspend fun setProfile(photoUri: String, name: String, url: String) =
        dataStore.updateData {
            it.toBuilder().apply {
                this.photoUri = photoUri
                this.name = name
                this.url = url
            }.build()
        }
}