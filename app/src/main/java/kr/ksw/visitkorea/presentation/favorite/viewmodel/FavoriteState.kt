package kr.ksw.visitkorea.presentation.favorite.viewmodel

import kr.ksw.visitkorea.domain.model.Favorite

data class FavoriteState(
    val favoriteList: List<Favorite> = emptyList()
)