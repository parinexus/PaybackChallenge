package payback.pixabay.challenge.data.datastore.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ImageRepositoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveImages(countries: List<ImageDataModel>)

    @Query("SELECT * FROM image_details WHERE searchQuery=:query")
    suspend fun fetchImagesByQuery(query: String): List<ImageDataModel>?

    @Query("DELETE  FROM image_details WHERE searchQuery=:query")
    suspend fun removeImagesByQuery(query: String)
}
