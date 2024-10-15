package kr.ksw.visitkorea.presentation.festival.viewmodel

import kr.ksw.visitkorea.data.local.entity.FavoriteEntity
import kr.ksw.visitkorea.presentation.common.DetailParcel

sealed interface FestivalActions {
    data class ClickFestivalCardItem(
        val data: DetailParcel
    ) : FestivalActions
    data class ClickFavoriteIcon(
        val entity: FavoriteEntity,
        val isFavorite: Boolean = false,
    ) : FestivalActions
}