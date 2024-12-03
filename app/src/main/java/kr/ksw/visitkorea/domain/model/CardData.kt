package kr.ksw.visitkorea.domain.model

sealed interface CardData {
    val title: String
    val address: String
    val dist: String?
    val firstImage: String
    val contentId: String
}