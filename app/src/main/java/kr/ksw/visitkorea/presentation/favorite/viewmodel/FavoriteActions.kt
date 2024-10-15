package kr.ksw.visitkorea.presentation.favorite.viewmodel

import kr.ksw.visitkorea.domain.model.Favorite

sealed interface FavoriteActions {
    data class ClickFavoriteIcon(val favorite: Favorite) : FavoriteActions
}