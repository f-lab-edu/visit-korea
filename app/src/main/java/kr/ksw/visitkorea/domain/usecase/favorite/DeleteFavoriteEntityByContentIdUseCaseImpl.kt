package kr.ksw.visitkorea.domain.usecase.favorite

import kr.ksw.visitkorea.data.local.dao.FavoriteDao
import javax.inject.Inject

class DeleteFavoriteEntityByContentIdUseCaseImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
) : DeleteFavoriteEntityByContentIdUseCase {
    override suspend fun invoke(contentId: String) {
        favoriteDao.deleteFavoriteEntityByContentId(contentId)
    }
}