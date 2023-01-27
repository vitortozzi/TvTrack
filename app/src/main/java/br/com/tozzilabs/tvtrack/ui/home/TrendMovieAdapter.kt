package br.com.tozzilabs.tvtrack.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.tozzilabs.tvtrack.databinding.LayoutMovieTrendItemBinding
import br.com.tozzilabs.tvtrack.model.Movie
import br.com.tozzilabs.tvtrack.model.getFormattedRating
import br.com.tozzilabs.tvtrack.model.getImageUrl
import com.bumptech.glide.Glide


class TrendMovieAdapter(
    private val movies: List<Movie>,
) : RecyclerView.Adapter<TrendMovieAdapter.TrendMovieHolder>() {

    companion object {
        private const val UNVOTED_VALUE = 0.0
    }

    var listener: ((Movie) -> Unit)? = null

    inner class TrendMovieHolder(private val binding: LayoutMovieTrendItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {

            binding.movieTitle.text = movie.title

            if(movie.voteAverage > UNVOTED_VALUE) {
                binding.movieRating.text = movie.getFormattedRating()
            } else binding.movieRating.visibility = View.GONE

            Glide.with(binding.root.context)
                .load(getImageUrl(movie.posterPath))
                .into(binding.moviePoster)

            binding.root.setOnClickListener {
                listener?.invoke(movie)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendMovieHolder {
        val itemBinding = LayoutMovieTrendItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrendMovieHolder(itemBinding)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: TrendMovieHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }
}