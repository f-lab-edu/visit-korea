package kr.ksw.visitkorea.domain.usecase.favorite

import kr.ksw.visitkorea.domain.model.Favorite

interface DeleteFavoriteEntityUseCase {
    suspend operator fun invoke(favorite: Favorite)
}