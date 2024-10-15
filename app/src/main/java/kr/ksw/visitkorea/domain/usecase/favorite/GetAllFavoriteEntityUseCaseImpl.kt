package kr.ksw.visitkorea.domain.usecase.favorite

import kotlinx.coroutines.flow.Flow
import kr.ksw.visitkorea.data.local.entity.FavoriteEntity
import kr.ksw.visitkorea.data.repository.FavoriteRepository
import javax.inject.Inject

class GetAllFavoriteEntityUseCaseImpl @Inject constructor(
    private val favoriteRepository: FavoriteRepository
): GetAllFavoriteEntityUseCase {
    override suspend fun invoke(): Flow<List<FavoriteEntity>> = favoriteRepository.getAllFavoriteEntity()
}