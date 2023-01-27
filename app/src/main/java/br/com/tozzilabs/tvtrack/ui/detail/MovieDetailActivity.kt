package br.com.tozzilabs.tvtrack.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import br.com.tozzilabs.tvtrack.R
import br.com.tozzilabs.tvtrack.databinding.ActivityMovieDetailBinding
import br.com.tozzilabs.tvtrack.model.Movie
import br.com.tozzilabs.tvtrack.model.getImageUrl
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

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
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        setupContent()
    }

    private fun setupContent() {
        val movie = args.movieArg
        handleMovieDetails(movie)
    }

    private fun handleMovieDetails(movie: Movie) {
        Glide.with(this)
            .load(getImageUrl(movie.backdropPath))
            .into(binding.moviePoster)
        binding.contentDescription.description?.text = movie.overview
    }

    override fun onBackPressed() {
        this.finish()
    }
}