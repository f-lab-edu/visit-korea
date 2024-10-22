package kr.ksw.visitkorea.presentation.detail.viewmodel

sealed class DetailUIEffect {
    data class OpenMapApplication(
        val lat: Double,
        val lng: Double,
        val name: String,
    ) : DetailUIEffect()
}