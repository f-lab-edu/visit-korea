package kr.ksw.visitkorea.domain.usecase.favorite

import kr.ksw.visitkorea.data.local.entity.FavoriteEntity

interface UpsertFavoriteEntityUseCase {
    suspend operator fun invoke(favoriteEntity: FavoriteEntity)
}