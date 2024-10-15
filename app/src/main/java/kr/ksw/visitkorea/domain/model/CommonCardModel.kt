package kr.ksw.visitkorea.domain.model

data class CommonCardModel(
    val address: String = "",
    val firstImage: String = "",
    val title: String = "",
    val dist: String? = null,
    val contentId: String = "",
    val contentTypeId: String = "",
)
