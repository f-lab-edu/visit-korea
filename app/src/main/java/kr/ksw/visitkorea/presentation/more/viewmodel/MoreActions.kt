package kr.ksw.visitkorea.presentation.more.viewmodel

import kr.ksw.visitkorea.presentation.common.DetailParcel

interface MoreActions {
    data class ClickCardItem(val data: DetailParcel) : MoreActions
}