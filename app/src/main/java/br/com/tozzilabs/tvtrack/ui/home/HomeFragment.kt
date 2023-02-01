package br.com.tozzilabs.tvtrack.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.com.tozzilabs.tvtrack.databinding.FragmentHomeBinding
import br.com.tozzilabs.tvtrack.data.model.Movie
import br.com.tozzilabs.tvtrack.ui.detail.MovieDetailActivityDirections
import br.com.tozzilabs.tvtrack.ui.home.adapter.DiscoverMovieAdapter

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel>()

    private val trendingAdapter = setupAdapter()
    private val topRatedAdapter = setupAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclers()
        setupObservers()
        setupListeners()
    }

    private fun setupRecyclers() {
        binding.homeContent.recycler.adapter = trendingAdapter
        binding.homeContent.recyclerTopRated.adapter = topRatedAdapter
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            homeViewModel.uiState.collectLatest { uiState ->
                handleState(uiState)
            }
        }
    }

    private fun handleState(homeViewState: HomeViewState) {
        when(homeViewState) {
            HomeViewState.Error -> showError()
            HomeViewState.Loading -> showLoading()
            is HomeViewState.DiscoverItemsLoaded -> {
                showContent()
                trendingAdapter.movies = homeViewState.discover.trend
                topRatedAdapter.movies = homeViewState.discover.topRated
            }
        }
    }

    private fun setupListeners() {
        binding.errorView.btnRetry.setOnClickListener {
            homeViewModel.loadDiscoverMovies()
        }
    }

    private fun showLoading() {
        binding.shimmerContent.shimmer.apply {
            startShimmer()
            visibility = View.VISIBLE
        }
        binding.homeContent.root.visibility = View.GONE
        binding.errorView.root.visibility = View.GONE
    }

    private fun showContent() {
        binding.homeContent.root.visibility = View.VISIBLE
        binding.shimmerContent.shimmer.visibility = View.GONE
        binding.errorView.root.visibility = View.GONE
    }

    private fun redirectToDetails(it: Movie) {
        val action = MovieDetailActivityDirections.startMovieDetails(it)
        findNavController().navigate(action)
    }

    private fun setupAdapter() =
        DiscoverMovieAdapter().apply {
            listener = {
                redirectToDetails(it)
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showError() {
        binding.errorView.root.visibility = View.VISIBLE
        binding.shimmerContent.shimmer.visibility = View.GONE
        binding.homeContent.root.visibility = View.GONE
    }
}