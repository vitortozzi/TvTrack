package br.com.tozzilabs.tvtrack.interactor.model

import br.com.tozzilabs.tvtrack.data.model.Movie

data class MovieDiscoveryVO(
    var trend: List<Movie> = listOf(),
    var topRated: List<Movie> = listOf()
)
