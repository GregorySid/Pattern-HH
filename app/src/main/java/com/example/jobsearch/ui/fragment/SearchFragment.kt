package com.example.jobsearch.ui.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobsearch.R
import com.example.jobsearch.databinding.FragmentSearchBinding
import com.example.jobsearch.ui.adapter.SearchAdapter
import com.example.jobsearch.ui.adapter.VacancieAdapter
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
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var vacancieAdapter: VacancieAdapter
    private val viewModel: SearchVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rcView()
        rcView_V()

        lifecycle.coroutineScope.launch {
            viewModel.viewState
                .flowWithLifecycle(lifecycle)
                .collect { state ->
                    renderState(state)
                }
        }
        binding.sweipL.setOnRefreshListener {
            viewModel.onRefresh()
        }

        binding.rcVacancies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 || (dy < 0 && binding.bLong.isShown)) {
                    binding.bLong.visibility = View.GONE
                }

                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val childCount = binding.rcVacancies.childCount
                    val itemCount = binding.rcVacancies.layoutManager?.itemCount
                    val firstVisibleItem =
                        (binding.rcVacancies.layoutManager as LinearLayoutManager)
                            .findFirstVisibleItemPosition()
                    if (firstVisibleItem + childCount == itemCount) {
                        binding.bLong.visibility = View.VISIBLE
                    }
                }
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    fun openNewTabHH(urls: String, context: Context) {
        val uris = Uri.parse(urls)
        val intents = Intent(Intent.ACTION_VIEW, uris)
        val b = Bundle()
        b.putBoolean("new_window", true)
        intents.putExtras(b)
        context.startActivity(intents)
    }

    private fun rcView() = with(binding) {
        rcView.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        searchAdapter = SearchAdapter {
            context?.let { it1 -> openNewTabHH(it.link, it1) }
        }
        rcView.adapter = searchAdapter
        searchAdapter.notifyDataSetChanged()
    }

    private fun rcView_V() = with(binding) {
        rcVacancies.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        vacancieAdapter = VacancieAdapter { item ->
            if (!item.isFavorite) {
                viewModel.addFavor(item)
                view?.let {
                    Snackbar.make(it, "Добавлено в избранное", Snackbar.LENGTH_SHORT)
                        .setAnchorView(binding.bLong).show()
                }
            } else {
                viewModel.deleteFavor(item)
                view?.let {
                    Snackbar.make(it, "Удалено из избранного", Snackbar.LENGTH_SHORT)
                        .setAnchorView(binding.bLong).show()
                }
            }
        }
        rcVacancies.adapter = vacancieAdapter
        vacancieAdapter.notifyDataSetChanged()
    }

    private fun renderState(state: MainVS) {
        when (state) {
            is Data -> renderData(state.dateOff)
            is Error -> renderError()
            is Loading -> renderLoading()
        }
    }

    private fun renderData(dateOff: DataOffers) = with(binding) {
        sweipL.isRefreshing = false
        searchAdapter.items = dateOff.offers
        vacancieAdapter.items = dateOff.vacancies

        val number = dateOff.vacancies.size
        tvOpenings.text = when (number) {
            null -> ""
            1 -> "$number вакансия"
            2, 3, 4 -> "$number вакансии"
            else -> "$number вакансий"
        }

        bLong.text = when (number) {
            null -> ""
            1 -> "Еще $number вакансия"
            2, 3, 4 -> "Еще $number вакансии"
            else -> "Еще $number вакансий"
        }

        bLong.setOnClickListener {
            rcView.visibility = View.GONE
            tvB1.visibility = View.GONE
            bLong.visibility = View.GONE
            llJob.visibility = View.VISIBLE
            ibSView.setImageResource(R.drawable.ic_output)
            viewModel.loadAllDate()
        }

        mCardView.setOnClickListener {
            tvSView.text = getString(R.string.search_text)
            rcView.visibility = View.GONE
            tvB1.visibility = View.GONE
            bLong.visibility = View.GONE
            llJob.visibility = View.VISIBLE
            ibSView.setImageResource(R.drawable.ic_output)
        }

        ibSView.setOnClickListener {
            tvSView.text = getString(R.string.look_text)
            rcView.visibility = View.VISIBLE
            tvB1.visibility = View.VISIBLE
            bLong.visibility = View.GONE
            llJob.visibility = View.GONE
            ibSView.setImageResource(R.drawable.ic_search)
        }
    }

    private fun renderError() = with(binding) {
        sweipL.isRefreshing = false
    }

    private fun renderLoading() = with(binding) {
        sweipL.isRefreshing = false
    }
}