package kr.ksw.visitkorea.presentation.hotel.viewmodel

import kr.ksw.visitkorea.presentation.common.DetailParcel

sealed interface HotelActions {
    data class ClickCardItem(val data: DetailParcel) : HotelActions
}