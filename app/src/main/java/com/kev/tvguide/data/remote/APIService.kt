package com.kev.tvguide.data.remote

import com.kev.tvguide.BuildConfig
import com.kev.tvguide.data.remote.details.DetailsResponse
import com.kev.tvguide.data.remote.popular.PopularResponse
import com.kev.tvguide.data.remote.popular.PopularResultDTO
import com.kev.tvguide.data.remote.search.SearchResponse
import com.kev.tvguide.data.remote.similar.SimilarResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @Headers("Authorization: ${BuildConfig.API_KEY}")
    @GET("popular")
    suspend fun getPopularShows(
        @Query("page") page: Int = 1,
     ): PopularResponse

    @GET("{series_id?}language=en-us")
    suspend fun getShowDetails(
        @Path("series_id") movieId : Int,
    ): DetailsResponse

    @GET("/search/tv?}")
    suspend fun searchShows(
        @Query("query")
        showName: String
    ): SearchResponse


    @GET("{series_id/similar}")
    suspend fun fetchSimilarShows(
        @Path("series_id") seriesID: Int
    ): SimilarResponse
}