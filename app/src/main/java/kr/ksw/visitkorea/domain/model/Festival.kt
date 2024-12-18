package kr.ksw.visitkorea.domain.model

data class Festival(
    val address: String = "",
    val firstImage: String = "",
    val title: String = "",
    val contentId: String = "",
    val eventStartDate: String = "",
    val eventEndDate: String = "",
    val isFavorite: Boolean = false,
)