package com.example.jobsearch.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobsearch.databinding.FragmentFavouritesBinding
import com.example.jobsearch.ui.adapter.FavouritesAdapter
import com.example.jobsearch.ui.dModel.DataOffers
import com.example.jobsearch.ui.vModel.Data
import com.example.jobsearch.ui.vModel.Error
import com.example.jobsearch.ui.vModel.Loading
import com.example.jobsearch.ui.vModel.MainVS
import com.example.jobsearch.ui.vModel.SearchVM
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouritesFragment : Fragment() {
    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var adapter: FavouritesAdapter
    private val viewModel: SearchVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rcView_Fav()

        lifecycle.coroutineScope.launch {
            viewModel.viewState
                .flowWithLifecycle(lifecycle)
                .collect { state ->
                    renderState(state)
                }
        }

        binding.sweipLayout.setOnRefreshListener {
            viewModel.onRefresh()
        }
    }

    private fun renderState(state: MainVS) {
        when (state) {
            is Data -> renderData(state.dateOff)
            is Error -> renderError()
            is Loading -> renderLoading()
        }
    }

    private fun renderData(dateOff: DataOffers) = with(binding) {
        sweipLayout.isRefreshing = false
        adapter.items = dateOff.vacancies
            .filter { it.isFavorite }

        val number = dateOff.vacancies.count{it.isFavorite}
        tvVac.text = when(number){
            null -> ""
            1 -> "$number вакансия"
            2, 3, 4 -> "$number вакансии"
            else -> "$number вакансий"
        }
    }

    private fun renderLoading() = with(binding) {
        sweipLayout.isRefreshing = false
    }

    private fun renderError() = with(binding) {
        sweipLayout.isRefreshing = false
    }

    private fun rcView_Fav() = with(binding) {
        rcView3.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter = FavouritesAdapter {
            viewModel.deleteFavor(it)
            adapter.notifyDataSetChanged()
            view?.let { Snackbar.make(it, "Удалено из избранного", Snackbar.LENGTH_SHORT)
                .setAnchorView(binding.tvV).show()}
        }
        rcView3.adapter = adapter
    }
}