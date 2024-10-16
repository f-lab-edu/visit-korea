package kr.ksw.visitkorea.presentation.favorite.viewmodel

import kr.ksw.visitkorea.domain.model.Favorite
import kr.ksw.visitkorea.presentation.common.DetailParcel

sealed interface FavoriteActions {
    data class ClickCardItem(val data: DetailParcel) : FavoriteActions
    data class ClickFavoriteIcon(val favorite: Favorite) : FavoriteActions
}