package com.pixabay.challenge.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pixabay.challenge.data.datastore.local.ImageLocalModel
import com.pixabay.challenge.data.datastore.local.ImageRepositoryDao

@Database(
    entities = [ImageLocalModel::class],
    version = AppStorage.VERSION,
    exportSchema = false
)
abstract class AppStorage : RoomDatabase() {

    abstract fun imageDao(): ImageRepositoryDao

    companion object {
        internal const val VERSION = 1
        private const val NAME = "image_db"

        fun create(applicationContext: Context): AppStorage {
            return Room.databaseBuilder(
                applicationContext,
                AppStorage::class.java,
                NAME
            ).fallbackToDestructiveMigration().build()
        }
    }
}
