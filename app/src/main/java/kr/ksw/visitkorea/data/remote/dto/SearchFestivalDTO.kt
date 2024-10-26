package kr.ksw.visitkorea.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SearchFestivalDTO(
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
    @SerializedName("eventstartdate")
    val eventStartDate: String,
    @SerializedName("eventenddate")
    val eventEndDate: String,
    @SerializedName("firstimage")
    val firstImage: String,
    @SerializedName("firstimage2")
    val firstImage2: String,
    @SerializedName("mapx")
    val mapX: String,
    @SerializedName("mapy")
    val mapY: String,
    val cat3: String,
    val tel: String,
    val title: String,
)