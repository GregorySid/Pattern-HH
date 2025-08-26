package com.example.jobsearch.ui.vModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobsearch.ui.dModel.DataOffers
import com.example.jobsearch.ui.dModel.DataVacancie
import com.example.jobsearch.ui.db.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchVM @Inject constructor(
    private val repo: Repo
) : ViewModel() {
    private val _viewState = MutableStateFlow<MainVS>(Loading)
    val viewState: Flow<MainVS> get() = _viewState
    private var offers: DataOffers? = null

    init {
        loadDate()
    }

    fun loadDate() {
        _viewState.value = Loading
        viewModelScope.launch {
            try {
                val date = repo.getJson()
                offers = date
                val favorites = repo.getFavorites()
                val result = DataOffers(
                    date.offers,
                    date.vacancies
                        .take(3)
                        .map { vacancy ->
                            vacancy.copy(isFavorite = favorites.any { f -> f.id == vacancy.id })
                        }
                )
                _viewState.value = Data(result)
            } catch (e: Exception) {
                _viewState.value = Error
                Log.e("SearchVM", "loading failed", e)
            }
        }
    }

    fun loadAllDate() {
        _viewState.value = Loading
        viewModelScope.launch {
            try {
                val date = offers ?: return@launch
                val favorites = repo.getFavorites()
                val result = DataOffers(
                    date.offers, date.vacancies.map { vacancy ->
                        vacancy.copy(isFavorite = favorites.any { f -> f.id == vacancy.id })
                    }.toMutableList()
                )
                _viewState.value = Data(result)
            } catch (e: Exception) {
                _viewState.value = Error
                Log.e("SearchVM", "loading failed", e)
            }
        }
    }

    fun updateFavourites() {
        viewModelScope.launch {
            try {
                val date = offers ?: return@launch
                val favorites = repo.getFavorites()
                val result = DataOffers(
                    date.offers, date.vacancies
                        .map { vacancy ->
                            vacancy.copy(isFavorite = favorites.any { f -> f.id == vacancy.id })
                        }
                        .toMutableList())
                _viewState.value = Data(result)
            } catch (e: Exception) {
                _viewState.value = Error
                Log.e("SearchVM", "loading failed", e)
            }
        }
    }

    fun onRefresh() {
        loadDate()
    }

    fun addFavor(item: DataVacancie) {
        viewModelScope.launch {
            repo.insert(item.copy(isFavorite = !item.isFavorite))
            updateFavourites()
        }
    }

    fun deleteFavor(item: DataVacancie) {
        viewModelScope.launch {
            repo.dalete(item)
            updateFavourites()
        }
    }
}