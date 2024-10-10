package kr.ksw.visitkorea.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LocationBasedDTO(
    @SerializedName("addr1")
    val address: String,
    @SerializedName("areacode")
    val areaCode: String,
    @SerializedName("sigungucode")
    val sigunguCode: String,
    @SerializedName("contentid")
    val contentId: String,
    @SerializedName("contenttypeid")
    val contentTypeId: String,
    val dist: String,
    @SerializedName("firstimage")
    val firstImage: String,
    @SerializedName("firstimage2")
    val firstImage2: String,
    @SerializedName("mapx")
    val mapX: String,
    @SerializedName("mapy")
    val mapY: String,
    val tel: String,
    val title: String,
)