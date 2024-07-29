package com.kev.tvguide.models.search


import com.google.gson.annotations.SerializedName
import android.support.annotation.Keep

@Keep
data class SearchResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)