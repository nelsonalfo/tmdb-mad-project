package com.example.tmdbmadproject.data.model

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("page") val page: Int?,
    @SerializedName("total_results") val totalResults: Int?,
    @SerializedName("total_pages") val totalPages: Int?,
    @SerializedName("results") val results: List<MovieResume>?
)

data class MovieResume(
    @SerializedName("vote_count") val voteCount: Int? = 0,
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("video") val video: Boolean? = false,
    @SerializedName("vote_average") val voteAverage: Double? = 0.0,
    @SerializedName("title") val title: String? = "",
    @SerializedName("popularity") val popularity: Double? = 0.0,
    @SerializedName("poster_path") val posterPath: String? = "",
    @SerializedName("original_language") val originalLanguage: String? = "",
    @SerializedName("original_title") val originalTitle: String? = "",
    @SerializedName("genre_ids") val genreIds: List<Int>? = emptyList(),
    @SerializedName("backdrop_path") val backdropPath: String? = "",
    @SerializedName("adult") val adult: Boolean? = false,
    @SerializedName("overview") val overview: String? = "",
    @SerializedName("release_date") val releaseDate: String? = ""
) {
    var posterUrl: String = ""
}

fun MovieResume.generatePosterUrl(configuration: TmdbConfiguration) = configuration.images?.let {
    it.baseUrl?.replace("http", "https") + it.posterSizes?.find { posterSize -> posterSize == "w500" } + posterPath
} ?: ""
