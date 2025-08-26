package com.example.jobsearch.ui.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jobsearch.ui.dModel.FavoriteVacancy

@Dao
interface FavouritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vacancy: FavoriteVacancy)

    @Delete
    suspend fun delete(item: FavoriteVacancy)

    @Query("SELECT * FROM favourites")
    suspend fun getAll():List<FavoriteVacancy>
}