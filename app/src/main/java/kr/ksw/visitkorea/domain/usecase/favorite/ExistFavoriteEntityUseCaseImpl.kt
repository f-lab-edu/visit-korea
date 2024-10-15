package kr.ksw.visitkorea.domain.usecase.favorite

import kr.ksw.visitkorea.data.repository.FavoriteRepository
import javax.inject.Inject

class ExistFavoriteEntityUseCaseImpl @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : ExistFavoriteEntityUseCase {
    override suspend fun invoke(contentId: String): Boolean =
        favoriteRepository.existFavoriteEntity(contentId) == 1
}