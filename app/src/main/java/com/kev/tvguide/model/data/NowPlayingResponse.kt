package com.kev.tvguide.model.data


import com.google.gson.annotations.SerializedName

data class NowPlayingResponse(

    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val movieItem: List<MovieItem>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)