package br.com.tozzilabs.tvtrack.model

import br.com.tozzilabs.tvtrack.data.model.IMAGES_PATH
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.time.LocalDate

data class Movie(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    val genres: List<Genre>,
    val id: Long,
    @SerializedName("imdb_id")
    val imdbID: String,
    val title: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    val mediaType: String,
    val genreIDS: List<Long>,
    val popularity: Double,
    @SerializedName("release_date")
    val releaseDate: String,
    val runtime: Long,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    val voteCount: Long,
    val tagline: String
): Serializable

data class Genre (
    val id: Long,
    val name: String
): Serializable

fun getImageUrl(imageName: String) : String {
    return "$IMAGES_PATH$imageName"
}

fun Movie.getFormattedRating() : String =
    String.format("%.1f", this.voteAverage)

fun Movie.getReleaseYear() : String =
    LocalDate.parse(this.releaseDate).year.toString()

fun Movie.getFormattedRuntime(): String {
    val minutesPerHour = 60
    val hours = this.runtime/minutesPerHour
    val minutes = this.runtime.mod(minutesPerHour)
    return "${hours}h ${minutes}m"
}

fun Movie.getFormattedGenre(): String {
    return this.genres.joinToString(separator = ", ") { it.name}
}