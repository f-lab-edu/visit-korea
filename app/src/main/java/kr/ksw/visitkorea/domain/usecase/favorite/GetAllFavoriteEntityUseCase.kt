package kr.ksw.visitkorea.domain.usecase.favorite

import kotlinx.coroutines.flow.Flow
import kr.ksw.visitkorea.data.local.entity.FavoriteEntity

interface GetAllFavoriteEntityUseCase {
    suspend operator fun invoke(): Flow<List<FavoriteEntity>>
}