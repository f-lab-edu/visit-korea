package kr.ksw.visitkorea.presentation.home.viewmodel

import kr.ksw.visitkorea.presentation.common.ContentType
import kr.ksw.visitkorea.presentation.common.DetailParcel

sealed class HomeUiEffect {
    data class StartMoreActivity(val contentType: ContentType) : HomeUiEffect()
    data class StartDetailActivity(val data: DetailParcel) : HomeUiEffect()
}