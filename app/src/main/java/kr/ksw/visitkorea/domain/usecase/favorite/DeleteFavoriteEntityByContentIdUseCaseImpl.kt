package kr.ksw.visitkorea.domain.usecase.favorite

import kr.ksw.visitkorea.data.repository.FavoriteRepository
import javax.inject.Inject

class DeleteFavoriteEntityByContentIdUseCaseImpl @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : DeleteFavoriteEntityByContentIdUseCase {
    override suspend fun invoke(contentId: String) {
        favoriteRepository.deleteFavoriteEntityByContentId(contentId)
    }
}