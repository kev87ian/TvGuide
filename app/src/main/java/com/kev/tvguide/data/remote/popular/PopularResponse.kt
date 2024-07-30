package com.kev.tvguide.data.remote.popular


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class PopularResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<PopularResultDTO>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)