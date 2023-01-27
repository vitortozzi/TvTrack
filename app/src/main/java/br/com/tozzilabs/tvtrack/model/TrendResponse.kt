package br.com.tozzilabs.tvtrack.model

data class TrendResponse (
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)