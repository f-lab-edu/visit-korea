package kr.ksw.visitkorea.domain.usecase.model

data class CommonCardModel(
    val address: String = "",
    val firstImage: String = "",
    val title: String = "",
    val dist: String? = null,
    val contentId: String = "",
    val contentTypeId: String = "",
)