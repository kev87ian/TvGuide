package com.kev.tvguide.models.details


import com.google.gson.annotations.SerializedName
import android.support.annotation.Keep

@Keep
data class SpokenLanguage(
    @SerializedName("english_name")
    val englishName: String,
    @SerializedName("iso_639_1")
    val iso6391: String,
    @SerializedName("name")
    val name: String
)