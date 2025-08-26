package com.example.jobsearch.ui.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.jobsearch.ui.dModel.FavoriteVacancy

const val DATA_CODE = 2

@Database(entities = [FavoriteVacancy::class], version = DATA_CODE, exportSchema = false)
@TypeConverters(Converter::class)
abstract class MainDb: RoomDatabase() {

    abstract fun favouriteDao(): FavouritesDao
}