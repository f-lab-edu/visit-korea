package kr.ksw.visitkorea.presentation.detail.viewmodel

sealed interface DetailHotelActions {
    data object OnClickFacilityInfoButton : DetailHotelActions
    data object OnClickRoomInfoButton : DetailHotelActions
}