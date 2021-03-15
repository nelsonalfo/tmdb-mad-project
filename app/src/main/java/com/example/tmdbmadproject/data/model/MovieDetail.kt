package com.example.tmdbmadproject.data.model

import com.google.gson.annotations.SerializedName


data class MovieDetail(
    @SerializedName("adult") var adult: Boolean = false,
    @SerializedName("backdrop_path") var backdropPath: String = "",
    @SerializedName("belongs_to_collection") var belongsToCollection: Any? = null,
    @SerializedName("budget") var budget: Int = 0,
    @SerializedName("genres") var genres: List<Genre> = emptyList(),
    @SerializedName("homepage") var homepage: String = "",
    @SerializedName("id") var id: Int = 0,
    @SerializedName("imdb_id") var imdbId: String = "",
    @SerializedName("original_language") var originalLanguage: String = "",
    @SerializedName("original_title") var originalTitle: String = "",
    @SerializedName("overview") var overview: String = "",
    @SerializedName("popularity") var popularity: Double = 0.0,
    @SerializedName("poster_path") var posterPath: String = "",
    @SerializedName("production_companies") var productionCompanies: List<ProductionCompany> = emptyList(),
    @SerializedName("production_countries") var productionCountries: List<ProductionCountry> = emptyList(),
    @SerializedName("release_date") var releaseDate: String = "",
    @SerializedName("revenue") var revenue: Int = 0,
    @SerializedName("runtime") var runtime: Int = 0,
    @SerializedName("spoken_languages") var spokenLanguages: List<SpokenLanguage> = emptyList(),
    @SerializedName("status") var status: String = "",
    @SerializedName("tagline") var tagline: String = "",
    @SerializedName("title") var title: String = "",
    @SerializedName("video") var video: Boolean = false,
    @SerializedName("vote_average") var voteAverage: Double = 0.0,
    @SerializedName("vote_count") var voteCount: Int = 0,
) {
    var backdropUrl: String = ""
    var posterUrl: String = ""
}

data class Genre(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("name") val name: String = ""
)

data class ProductionCompany(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("name") var name: String = ""
)

data class ProductionCountry(
    @SerializedName("iso_3166_1") var countryCode: String = "",
    @SerializedName("name") var name: String = ""
)

data class SpokenLanguage(
    @SerializedName("iso_639_1") var languageCode: String = "",
    @SerializedName("name") var name: String = ""
)

fun List<Genre>.formatted(): String = map { genre -> genre.name }.reduce { prevGenres, genre -> "$prevGenres, $genre" }