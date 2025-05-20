package com.example.movies.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.consecutivep.domain.model.ProfileEntity
import com.example.movies.data.serializer.ProfileSerializer

class SourceProvider(val context: Context) {
    private val Context.profileDataStore: DataStore<ProfileEntity> by dataStore(
        fileName = "profile.pb",
        serializer = ProfileSerializer
    )

    fun provide() = context.profileDataStore
}