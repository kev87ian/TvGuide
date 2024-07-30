package com.kev.tvguide.data.remote.similar


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class SimilarResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)