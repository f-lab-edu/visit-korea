package kr.ksw.visitkorea.presentation.festival.viewmodel

import kr.ksw.visitkorea.presentation.common.DetailParcel

sealed class FestivalUiEffect {
    data class StartDetailActivity(
        val data: DetailParcel
    ) : FestivalUiEffect()
}