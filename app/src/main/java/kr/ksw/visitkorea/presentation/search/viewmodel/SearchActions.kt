package kr.ksw.visitkorea.presentation.search.viewmodel

import kr.ksw.visitkorea.presentation.common.DetailParcel

sealed interface SearchActions {
    data object SubmitSearchKeyword : SearchActions
    data class UpdateSearchKeyword(val newKeyword: String) : SearchActions
    data class ClickCardItem(val data: DetailParcel) : SearchActions
}