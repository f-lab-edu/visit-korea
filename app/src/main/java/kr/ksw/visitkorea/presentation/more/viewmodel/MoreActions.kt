package kr.ksw.visitkorea.presentation.more.viewmodel

import kr.ksw.visitkorea.data.local.entity.FavoriteEntity
import kr.ksw.visitkorea.presentation.common.DetailParcel

interface MoreActions {
    data class ClickFavoriteIconUpsert(
        val favorite: FavoriteEntity,
    ) : MoreActions
    data class ClickFavoriteIconDelete(
        val contentId: String,
    ) : MoreActions
    data class ClickCardItem(val data: DetailParcel) : MoreActions
}