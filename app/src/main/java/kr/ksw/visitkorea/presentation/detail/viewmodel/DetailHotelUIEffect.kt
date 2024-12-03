package kr.ksw.visitkorea.presentation.detail.viewmodel

sealed class DetailHotelUIEffect {
    data class OpenMapApplication(
        val lat: String,
        val lng: String,
        val name: String,
    ) : DetailHotelUIEffect()
}