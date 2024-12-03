package kr.ksw.visitkorea.domain.usecase.favorite

interface ExistFavoriteEntityUseCase {
    suspend operator fun invoke(contentId: String): Boolean
}