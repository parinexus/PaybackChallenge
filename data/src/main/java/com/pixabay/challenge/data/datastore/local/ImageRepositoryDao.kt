package com.pixabay.challenge.data.datastore.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageRepositoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveImages(countries: List<ImageLocalModel>)

    @Query("SELECT * FROM image_details WHERE searchQuery=:query")
    fun fetchImagesByQuery(query: String): Flow<List<ImageLocalModel>>

    @Query("DELETE  FROM image_details WHERE searchQuery=:query")
    suspend fun removeImagesByQuery(query: String)

    @Query("SELECT * FROM image_details WHERE searchQuery = :query")
    fun fetchImagesByQueryWithTimestamps(query: String): Flow<List<ImageLocalModel>>

    @Query("SELECT MAX(createdAt) FROM image_details")
    suspend fun getLastUpdateTime(): Long?
}
