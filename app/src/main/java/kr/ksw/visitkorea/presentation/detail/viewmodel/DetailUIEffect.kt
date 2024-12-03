package kr.ksw.visitkorea.presentation.detail.viewmodel

sealed class DetailUIEffect {
    data class OpenMapApplication(
        val lat: String,
        val lng: String,
        val name: String,
    ) : DetailUIEffect()
}