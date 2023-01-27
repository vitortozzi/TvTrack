package br.com.tozzilabs.tvtrack.model

import br.com.tozzilabs.tvtrack.data.model.IMAGES_PATH
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Movie(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    val id: Long,
    val title: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    val mediaType: String,
    val genreIDS: List<Long>,
    val popularity: Double,
    val releaseDate: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    val voteCount: Long
): Serializable

fun getImageUrl(imageName: String) : String {
    return "$IMAGES_PATH$imageName"
}

fun Movie.getFormattedRating() : String =
    String.format("%.1f", this.voteAverage)

