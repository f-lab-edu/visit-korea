package kr.ksw.visitkorea.presentation.detail.viewmodel

sealed interface DetailHotelActions {
    data object OnClickFacilityInfoButton : DetailHotelActions
    data object OnClickRoomInfoButton : DetailHotelActions
    data class ClickDetailImages(
        val selectedImage: Int,
    ) : DetailHotelActions
    data class ClickRoomDetailImages(
        val selectedImage: Int,
        val selectedRoomIndex: Int,
    ) : DetailHotelActions
    data object ClickBackButtonWhenViewPagerOpened : DetailHotelActions
    data object ClickViewMapButton : DetailHotelActions
}