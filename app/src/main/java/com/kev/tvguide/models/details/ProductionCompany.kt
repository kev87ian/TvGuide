package com.kev.tvguide.models.details


import com.google.gson.annotations.SerializedName
import android.support.annotation.Keep

@Keep
data class ProductionCompany(
    @SerializedName("id")
    val id: Int,
    @SerializedName("logo_path")
    val logoPath: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("origin_country")
    val originCountry: String
)