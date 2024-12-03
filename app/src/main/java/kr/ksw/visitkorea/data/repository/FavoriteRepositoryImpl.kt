package kr.ksw.visitkorea.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kr.ksw.visitkorea.data.local.dao.FavoriteDao
import kr.ksw.visitkorea.data.local.entity.FavoriteEntity
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
): FavoriteRepository {
    override suspend fun upsertFavoriteEntity(favoriteEntity: FavoriteEntity) {
        favoriteDao.upsertFavoriteEntity(favoriteEntity)
    }

    override suspend fun deleteFavoriteEntity(favoriteEntity: FavoriteEntity) {
        favoriteDao.deleteFavoriteEntity(favoriteEntity)
    }

    override suspend fun deleteFavoriteEntityByContentId(contentId: String) {
        favoriteDao.deleteFavoriteEntityByContentId(contentId)
    }

    override suspend fun getAllFavoriteEntity(): Flow<List<FavoriteEntity>> = flow {
        favoriteDao.getAllFavoriteEntity().collect {
            emit(it)
        }
    }.catch { emit(emptyList()) }
        .flowOn(Dispatchers.IO)

    override suspend fun existFavoriteEntity(contentId: String): Int = favoriteDao.existFavoriteEntity(contentId)
}