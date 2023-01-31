package br.com.tozzilabs.tvtrack.data.model

data class DiscoveryResponse (
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)