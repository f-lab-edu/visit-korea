package kr.ksw.visitkorea.presentation.more.viewmodel

import kr.ksw.visitkorea.presentation.common.DetailParcel

sealed class MoreUiEffect {
    data class StartDetailActivity(
        val data: DetailParcel
    ) : MoreUiEffect()
}