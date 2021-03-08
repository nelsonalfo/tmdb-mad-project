package com.example.tmdbmadproject.data.model

import com.google.gson.annotations.SerializedName


data class TmdbConfiguration(
    @SerializedName("images") var images: Images? = null,
    @SerializedName("change_keys") var changeKeys: List<String>?
)

data class Images(
    @SerializedName("base_url") var baseUrl: String?,
    @SerializedName("secure_base_url") var secureBaseUrl: String?,
    @SerializedName("backdrop_sizes") var backdropSizes: List<String>?,
    @SerializedName("logo_sizes") var logoSizes: List<String>?,
    @SerializedName("poster_sizes") var posterSizes: List<String>?,
    @SerializedName("profile_sizes") var profileSizes: List<String>?,
    @SerializedName("still_sizes") var stillSizes: List<String>?
)