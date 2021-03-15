package com.example.tmdbmadproject.data.model

import com.google.gson.annotations.SerializedName


data class TmdbConfiguration(
    @SerializedName("images") var images: Images? = null,
    @SerializedName("change_keys") var changeKeys: List<String>? = null
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

fun Images.generatePosterUrl(posterPath: String): String {
    return generateImageUrl(imageSizes = posterSizes, targetSize = "w500", imagePath = posterPath)
}

fun Images.generateBackdropUrl(backdropPath: String): String {
    return generateImageUrl(imageSizes = backdropSizes, targetSize = "w1280", imagePath = backdropPath)
}


private fun Images.generateImageUrl(imageSizes: List<String>?, targetSize: String, imagePath: String): String {
    return if (imageSizes.isNullOrEmpty() || imagePath.isEmpty()) {
        ""
    } else {
        val imageSize = imageSizes.find { imageSize -> imageSize == targetSize } ?: imageSizes[0]
        "$secureBaseUrl$imageSize$imagePath"
    }
}