package com.kev.tvguide.models.details


import com.google.gson.annotations.SerializedName
import android.support.annotation.Keep

@Keep
data class ProductionCountry(
    @SerializedName("iso_3166_1")
    val iso31661: String,
    @SerializedName("name")
    val name: String
)