package kr.ksw.visitkorea.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DetailImageDTO(
    @SerializedName("originimgurl")
    val originImgUrl: String,
    @SerializedName("smallimageurl")
    val smallImageUrl: String
)
