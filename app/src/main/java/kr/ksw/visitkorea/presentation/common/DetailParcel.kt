package kr.ksw.visitkorea.presentation.common

data class DetailParcel(
    val title: String,
    val firstImage: String,
    val address: String,
    val dist: String?,
    val contentId: String,
    val contentTypeId: String
)