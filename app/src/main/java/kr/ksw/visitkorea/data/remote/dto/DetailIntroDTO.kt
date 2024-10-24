package kr.ksw.visitkorea.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DetailIntroDTO(
    // TouristSpot
    val parking: String?,
    @SerializedName("restdate")
    val restDate: String?,
    @SerializedName("usetime")
    val useTime: String?,
    // Culture Center
    @SerializedName("parkingculture")
    val parkingCulture: String?,
    @SerializedName("restdateculture")
    val restDateCulture: String?,
    @SerializedName("usefee")
    val useFee: String?,
    @SerializedName("usetimeculture")
    val useTimeCulture: String?,
    // Festival
    @SerializedName("eventplace")
    val eventPlace: String?,
    @SerializedName("playtime")
    val playTime: String?,
    @SerializedName("usetimefestival")
    val useTimeFestival: String?,
    // Leisure Sports
    @SerializedName("parkingleports")
    val parkingLeports: String?,
    @SerializedName("restdateleports")
    val restDateLeports: String?,
    @SerializedName("usefeeleports")
    val useFeeLeports: String?,
    @SerializedName("usetimeleports")
    val useTimeLeports: String?,
    // Restaurant
    @SerializedName("parkingfood")
    val parkingFood: String?,
    @SerializedName("restdatefood")
    val restDateFood: String?,
    @SerializedName("opentimefood")
    val openTimeFood: String?,
    @SerializedName("firstmenu")
    val firstMenu: String?,
    // Hotel
    @SerializedName("checkintime")
    val checkInTime: String?,
    @SerializedName("checkouttime")
    val checkOutTime: String?,
    @SerializedName("subfacility")
    val subFacility: String?,
    @SerializedName("reservationurl")
    val reservationUrl: String?
)
