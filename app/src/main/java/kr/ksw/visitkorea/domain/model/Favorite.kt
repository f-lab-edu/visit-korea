package kr.ksw.visitkorea.domain.model

data class Favorite(
    val id: Int,
    val title: String,
    val firstImage: String,
    val address: String,
    val dist: String?,
    val contentId: String,
    val contentTypeId: String,
    val eventStartDate: String?,
    val eventEndDate: String?,
)