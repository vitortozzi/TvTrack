package br.com.tozzilabs.tvtrack.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.tozzilabs.tvtrack.databinding.LayoutMovieDiscoverItemBinding
import br.com.tozzilabs.tvtrack.data.model.Movie
import br.com.tozzilabs.tvtrack.data.model.getFormattedRating
import br.com.tozzilabs.tvtrack.data.model.getImageUrl
import com.bumptech.glide.Glide


class DiscoverMovieAdapter : RecyclerView.Adapter<DiscoverMovieAdapter.DiscoverMovieViewHolder>() {

    var movies: List<Movie> = listOf()
    @SuppressLint("NotifyDataSetChanged")
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    companion object {
        private const val UNVOTED_VALUE = 0.0
    }

    var listener: ((Movie) -> Unit)? = null

    inner class DiscoverMovieViewHolder(private val binding: LayoutMovieDiscoverItemBinding) : RecyclerView.ViewHolder(binding.root) {
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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverMovieViewHolder {
        val itemBinding = LayoutMovieDiscoverItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DiscoverMovieViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: DiscoverMovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }
}