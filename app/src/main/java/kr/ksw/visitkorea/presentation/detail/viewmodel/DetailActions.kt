package kr.ksw.visitkorea.presentation.detail.viewmodel

import kr.ksw.visitkorea.data.local.entity.FavoriteEntity

sealed interface DetailActions {
    data class ClickFavoriteIconUpsert(
        val favorite: FavoriteEntity,
    ) : DetailActions
    data class ClickFavoriteIconDelete(
        val contentId: String,
    ) : DetailActions
}