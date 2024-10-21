package kr.ksw.visitkorea.presentation.home.viewmodel

import kr.ksw.visitkorea.presentation.common.ContentType

sealed class HomeUiEffect {
    data class StartHomeActivity(val contentType: ContentType) : HomeUiEffect()
}