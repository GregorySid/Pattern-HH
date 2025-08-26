package com.example.jobsearch.ui.db

import com.example.jobsearch.ui.apicreator.MainData
import com.example.jobsearch.ui.dModel.DataVacancie
import com.example.jobsearch.ui.dModel.FavoriteVacancy
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repo
@Inject constructor (private val bd: MainDb, private val remoteData : MainData) {

    suspend fun insert(item: DataVacancie) = bd.favouriteDao().insert(FavoriteVacancy(item.id))
    suspend fun dalete(item: DataVacancie) = bd.favouriteDao().delete(FavoriteVacancy(item.id))
    suspend fun getFavorites() = bd.favouriteDao().getAll()
    suspend fun getJson() = remoteData.getUse()
}