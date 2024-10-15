package kr.ksw.visitkorea.domain.usecase.favorite

import kr.ksw.visitkorea.data.local.entity.FavoriteEntity
import kr.ksw.visitkorea.data.repository.FavoriteRepository
import javax.inject.Inject

class UpsertFavoriteEntityUseCaseImpl @Inject constructor(
    private val favoriteRepository: FavoriteRepository
): UpsertFavoriteEntityUseCase {
    override suspend fun invoke(favoriteEntity: FavoriteEntity) {
        if(favoriteRepository.existFavoriteEntity(favoriteEntity.contentId) == 0) {
            favoriteRepository.upsertFavoriteEntity(favoriteEntity)
        }
    }
}