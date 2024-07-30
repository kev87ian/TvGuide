package com.kev.tvguide.data.remote.search


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class SearchResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<SearchResultDTO>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)