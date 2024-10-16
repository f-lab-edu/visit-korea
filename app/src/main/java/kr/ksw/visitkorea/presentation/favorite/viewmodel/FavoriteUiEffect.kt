package kr.ksw.visitkorea.presentation.favorite.viewmodel

import kr.ksw.visitkorea.presentation.common.DetailParcel

sealed class FavoriteUiEffect {
    data class StartDetailActivity(
        val data: DetailParcel
    ) : FavoriteUiEffect()
}