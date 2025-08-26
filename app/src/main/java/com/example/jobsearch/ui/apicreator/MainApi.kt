package com.example.jobsearch.ui.apicreator

import com.example.jobsearch.ui.dModel.DataOffers
import retrofit2.http.GET

interface MainApi {

    @GET("u/0/uc?id=1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r&export=download")
    suspend fun getJson(): DataOffers
}