package kr.ksw.visitkorea.domain.model

data class CommonCardModel(
    override val title: String = "",
    override val address: String = "",
    override val dist: String? = null,
    override val firstImage: String = "",
    override val contentId: String = "",
    val contentTypeId: String = "",
    val isFavorite: Boolean = false,
) : CardData
