package com.example.movies.domain

import com.example.consecutivep.domain.model.ProfileEntity
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalTime

interface IProfileRepository {
    suspend fun getProfile(): ProfileEntity?
    suspend fun setProfile(
        photoUri: String,
        name: String,
        url: String,
        time: LocalTime,
    ): ProfileEntity
    suspend fun observeProfile(): Flow<ProfileEntity>
}