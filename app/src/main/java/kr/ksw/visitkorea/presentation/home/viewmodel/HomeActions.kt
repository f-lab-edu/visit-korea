package kr.ksw.visitkorea.presentation.home.viewmodel

import kr.ksw.visitkorea.presentation.common.ContentType
import kr.ksw.visitkorea.presentation.common.DetailParcel

sealed interface HomeActions {
    data class ClickMoreButton(val contentType: ContentType) : HomeActions
    data class ClickCardItem(val data: DetailParcel) : HomeActions
}