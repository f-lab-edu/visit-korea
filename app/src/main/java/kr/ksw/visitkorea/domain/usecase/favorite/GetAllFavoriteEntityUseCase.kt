package kr.ksw.visitkorea.domain.usecase.favorite

import kotlinx.coroutines.flow.Flow
import kr.ksw.visitkorea.domain.model.Favorite

interface GetAllFavoriteEntityUseCase {
    suspend operator fun invoke(): Flow<List<Favorite>>
}