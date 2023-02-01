package br.com.tozzilabs.tvtrack.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navArgs
import br.com.tozzilabs.tvtrack.R
import br.com.tozzilabs.tvtrack.data.model.*
import br.com.tozzilabs.tvtrack.databinding.ActivityMovieDetailBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private val viewModel: MovieDetailViewModel by viewModels()

    private val args: MovieDetailActivityArgs by navArgs()

    private lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        setupObserver()
        viewModel.fetchDetails(args.movieArg.id)
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            viewModel.uiState.collectLatest { uiState ->
                handleMovieDetails(uiState)
            }
        }
    }

    private fun handleMovieDetails(detailViewState: DetailViewState?) {
        when (detailViewState) {
            is DetailViewState.DetailLoaded -> setupContent(detailViewState.details)
            DetailViewState.Error -> Unit //TODO
            DetailViewState.Loading -> showLoading()
            null -> Unit
        }
    }

    private fun showLoading() {
        binding.loadingView.visibility = View.VISIBLE
        binding.contentDescription.root.visibility = View.GONE
    }

    private fun setupContent(movie: Movie) {

        binding.loadingView.visibility = View.GONE
        binding.contentDescription.root.visibility = View.VISIBLE

        Glide.with(this)
            .load(getImageUrl(movie.backdropPath))
            .into(binding.movieBackdrop)

        Glide.with(this)
            .load(getImageUrl(movie.posterPath))
            .into(binding.contentDescription.moviePoster)

        with(binding.contentDescription) {
            description.text = movie.overview
            movieTitle.text = movie.title
            movieRating.text = movie.getFormattedRating()
            movieRelease.text = movie.getReleaseYear()
            movieLenght.text = movie.getFormattedRuntime()
            movieGenders.text = movie.getFormattedGenre()
            movieTagline.text= movie.tagline
            shortcutImdb.setOnClickListener {
                startActivity(
                    Intent(
                        "android.intent.action.VIEW",
                        Uri.parse("${IMDB_PATH}${movie.imdbID}")
                    )
                )
            }
        }
    }
}

sealed class DetailViewState {
    data class DetailLoaded(val details: Movie): DetailViewState()
    object Loading: DetailViewState()
    object Error: DetailViewState()
}