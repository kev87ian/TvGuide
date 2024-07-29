package com.kev.tvguide.models.details


import com.google.gson.annotations.SerializedName
import android.support.annotation.Keep

@Keep
data class Genre(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)