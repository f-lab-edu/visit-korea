package kr.ksw.visitkorea.domain.usecase.favorite

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.ksw.visitkorea.data.repository.FavoriteRepository
import kr.ksw.visitkorea.domain.model.Favorite
import kr.ksw.visitkorea.domain.usecase.mapper.toFavorite
import javax.inject.Inject

class GetAllFavoriteEntityUseCaseImpl @Inject constructor(
    private val favoriteRepository: FavoriteRepository
): GetAllFavoriteEntityUseCase {
    override suspend fun invoke(): Flow<List<Favorite>> =
        favoriteRepository.getAllFavoriteEntity().map {
            it.map { entity ->
                entity.toFavorite()
            }
        }
}