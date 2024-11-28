package kr.ksw.visitkorea.presentation.hotel.viewmodel

import kr.ksw.visitkorea.presentation.common.DetailParcel

sealed class HotelUiEffect {
    data class StartDetailActivity(
        val data: DetailParcel
    ) : HotelUiEffect()
}