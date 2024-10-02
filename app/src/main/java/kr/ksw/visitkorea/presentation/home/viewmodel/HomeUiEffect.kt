package kr.ksw.visitkorea.presentation.home.viewmodel

sealed class HomeUiEffect {
    data class StartHomeActivity(val contentTypeId: String) : HomeUiEffect()
}