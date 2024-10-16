package kr.ksw.visitkorea.domain.usecase.favorite

import kr.ksw.visitkorea.data.repository.FavoriteRepository
import kr.ksw.visitkorea.domain.model.Favorite
import kr.ksw.visitkorea.domain.usecase.mapper.toEntity
import javax.inject.Inject

class DeleteFavoriteEntityUseCaseImpl @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : DeleteFavoriteEntityUseCase {
    override suspend fun invoke(favorite: Favorite) {
        favoriteRepository.deleteFavoriteEntity(
            favorite.toEntity()
        )
    }
}