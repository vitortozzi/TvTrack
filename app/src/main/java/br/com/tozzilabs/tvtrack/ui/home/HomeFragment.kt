package br.com.tozzilabs.tvtrack.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.com.tozzilabs.tvtrack.databinding.FragmentHomeBinding
import br.com.tozzilabs.tvtrack.model.Movie
import br.com.tozzilabs.tvtrack.ui.detail.MovieDetailActivityDirections
import br.com.tozzilabs.tvtrack.ui.home.adapter.DiscoverMovieAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

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
    }

    private fun setupRecyclers() {
        binding.homeContent.recycler.adapter = trendingAdapter
        binding.homeContent.recyclerTopRated.adapter = topRatedAdapter
    }

    private fun setupObservers() {
        homeViewModel.viewState.observe(viewLifecycleOwner, Observer(::handleState))
    }

    private fun handleState(homeViewState: HomeViewState?) {
        when(homeViewState) {
            HomeViewState.Error -> showError("Error while fetching movies...")
            HomeViewState.Loading -> showLoading()
            is HomeViewState.TrendLoaded -> {
                showContent()
                trendingAdapter.movies = homeViewState.movies
            }
            is HomeViewState.TopRatedLoaded -> {
                showContent()
                topRatedAdapter.movies = homeViewState.movies
            }
            null -> {}
        }
    }

    private fun showLoading() {
        binding.shimmerContent.shimmer.apply {
            startShimmer()
            visibility = View.VISIBLE
        }
    }

    private fun showContent() {
        binding.shimmerContent.shimmer.visibility = View.GONE
        binding.homeContent.root.visibility = View.VISIBLE
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

    private fun showError(msg: String) {
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_INDEFINITE).setAction("DISMISS") {
        }.show()
    }
}