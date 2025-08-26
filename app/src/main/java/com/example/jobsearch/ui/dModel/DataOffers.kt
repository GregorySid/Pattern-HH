package com.example.jobsearch.ui.dModel

import javax.inject.Inject

data class DataOffers @Inject constructor(
    val offers: List<DataOffer> = emptyList(),
    val vacancies: List<DataVacancie> = emptyList()
)

/** Список рекомендаций */
data class DataOffer(
    val id: String? = null,
    val title: String = "",
    val link: String = "",
    val button: DataButton? = null
)

data class DataButton(
    val text: String = ""
)
