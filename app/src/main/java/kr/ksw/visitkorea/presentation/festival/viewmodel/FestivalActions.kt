package kr.ksw.visitkorea.presentation.festival.viewmodel

import kr.ksw.visitkorea.presentation.common.DetailParcel

sealed interface FestivalActions {
    data class ClickFestivalCardItem(
        val data: DetailParcel
    ) : FestivalActions
}