package kr.ksw.visitkorea.domain.usecase.favorite

interface DeleteFavoriteEntityByContentIdUseCase {
    suspend operator fun invoke(contentId: String)
}