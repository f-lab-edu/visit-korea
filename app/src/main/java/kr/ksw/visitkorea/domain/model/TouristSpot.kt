package kr.ksw.visitkorea.domain.model

data class TouristSpot(
    override val title: String = "",
    override val address: String = "",
    override val dist: String = "",
    override val firstImage: String = "",
    override val contentId: String = "",
) : CardData