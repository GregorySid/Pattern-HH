package com.example.jobsearch.ui.apicreator

import javax.inject.Inject

class MainData
@Inject constructor(private val mainApi : MainApi) {

    suspend fun getUse() = mainApi.getJson()
}
