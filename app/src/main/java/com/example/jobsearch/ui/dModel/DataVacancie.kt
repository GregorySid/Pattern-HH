package com.example.jobsearch.ui.dModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import javax.inject.Inject

@Entity(tableName = "vacancie")
data class DataVacancie @Inject constructor(
    @PrimaryKey
    @ColumnInfo
    val id: String = "",
    val lookingNumber: Int? = null,
    val title: String = "",
    val address: DataAddress = DataAddress(),
    val company: String = "",
    val experience: DataExperience = DataExperience(),
    val isFavorite: Boolean = false,
    val publishedDate: String = ""
): Serializable

@Entity(tableName = "favourites")
data class FavoriteVacancy(
    @PrimaryKey
    val id: String
)

data class DataAddress(
    val town: String = ""
)

data class DataExperience(
    val previewText: String = "",
    val text: String = ""
)

