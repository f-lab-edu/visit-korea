package kr.ksw.visitkorea.presentation.more.viewmodel

sealed interface SearchActions {
    data object SubmitSearchKeyword : SearchActions
    data class UpdateSearchKeyword(val newKeyword: String) : SearchActions
}