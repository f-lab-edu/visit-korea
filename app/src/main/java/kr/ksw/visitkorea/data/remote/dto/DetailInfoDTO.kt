package kr.ksw.visitkorea.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DetailInfoDTO (
    @SerializedName("roomtitle")
    val roomTitle: String,
    @SerializedName("roomsize1")
    val roomSize: String,
    @SerializedName("roomsize2")
    val roomSize2: String,
    @SerializedName("roombasecount")
    val roomBaseCount: String,
    @SerializedName("roommaxcount")
    val roomMaxCount: String,
    @SerializedName("roomoffseasonminfee1")
    val roomOffSeasonMinFee1: String, // 비수기주중최소
    @SerializedName("roomoffseasonminfee2")
    val roomoffseasonminfee2: String, // 비수기주말최소
    @SerializedName("roompeakseasonminfee1")
    val roomPeakSeasonMinfee1: String, // 성수기주중최소
    @SerializedName("roompeakseasonminfee2")
    val roomPeakSeasonMinfee2: String, // 성수기주말최소
    @SerializedName("roombathfacility")
    val roomBathFacility: String,
    @SerializedName("roomtv")
    val roomTv: String,
    @SerializedName("roompc")
    val roomPc: String,
    @SerializedName("roominternet")
    val roomInternet: String,
    @SerializedName("roomrefrigerator")
    val roomRefrigerator: String,
    @SerializedName("roomhairdryer")
    val roomHairdryer: String,
    @SerializedName("roomimg1")
    val roomImg1: String,
    @SerializedName("roomimg2")
    val roomImg2: String,
    @SerializedName("roomimg3")
    val roomImg3: String,
    @SerializedName("roomimg4")
    val roomImg4: String,
    @SerializedName("roomimg5")
    val roomImg5: String,
)