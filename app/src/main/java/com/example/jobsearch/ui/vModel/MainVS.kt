package com.example.jobsearch.ui.vModel

import com.example.jobsearch.ui.dModel.DataOffers

sealed interface MainVS

data object Loading: MainVS
data class Data (val dateOff: DataOffers): MainVS
data object Error: MainVS