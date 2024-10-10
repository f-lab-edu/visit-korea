package kr.ksw.visitkorea.presentation.search.viewmodel

import kr.ksw.visitkorea.presentation.common.DetailParcel

sealed class SearchUiEffect {
    data class StartDetailActivity(val data: DetailParcel) : SearchUiEffect()
}