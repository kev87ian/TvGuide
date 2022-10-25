package com.kev.tvguide.models


import com.google.gson.annotations.SerializedName

data class MoviesResponse(
	@SerializedName("page")
    val page: Int,
	@SerializedName("results")
    val movieItems: List<MovieItem>,
	@SerializedName("total_pages")
    val totalPages: Int,
	@SerializedName("total_results")
    val totalResults: Int
)