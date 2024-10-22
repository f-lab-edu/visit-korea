package kr.ksw.visitkorea.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DetailCommonDTO(
    @SerializedName("mapy")
    val lat: String,
    @SerializedName("mapx")
    val lng: String,
    val homepage: String,
    val overview: String,
)