package kr.ksw.visitkorea.domain.model

data class Restaurant(
    override val title: String = "",
    override val address: String = "",
    override val dist: String = "",
    override val firstImage: String = "",
    override val contentId: String = "",
    val firstImage2: String = "",
    val category: String = ""
) : CardData