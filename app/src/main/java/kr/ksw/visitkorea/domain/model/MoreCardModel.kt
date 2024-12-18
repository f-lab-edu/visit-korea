package kr.ksw.visitkorea.domain.model

data class MoreCardModel(
    val address: String = "",
    val firstImage: String = "",
    val title: String = "",
    val dist: String = "",
    val contentId: String = "",
    val category: String?,
    val isFavorite: Boolean = false,
)