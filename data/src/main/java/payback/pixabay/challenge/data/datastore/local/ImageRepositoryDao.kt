package payback.pixabay.challenge.data.datastore.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ImageRepositoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveImages(countries: List<ImageLocalModel>)

    @Query("SELECT * FROM image_details WHERE searchQuery=:query")
    suspend fun fetchImagesByQuery(query: String): List<ImageLocalModel>?

    @Query("DELETE  FROM image_details WHERE searchQuery=:query")
    suspend fun removeImagesByQuery(query: String)

    @Query("SELECT * FROM image_details WHERE searchQuery = :query")
    suspend fun fetchImagesByQueryWithTimestamps(query: String): List<ImageLocalModel>?
}
