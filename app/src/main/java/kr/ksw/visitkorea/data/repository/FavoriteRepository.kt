package kr.ksw.visitkorea.data.repository

import kotlinx.coroutines.flow.Flow
import kr.ksw.visitkorea.data.local.entity.FavoriteEntity

interface FavoriteRepository {
    suspend fun upsertFavoriteEntity(favoriteEntity: FavoriteEntity)

    suspend fun deleteFavoriteEntity(favoriteEntity: FavoriteEntity)

    suspend fun deleteFavoriteEntityByContentId(contentId: String)

    suspend fun getAllFavoriteEntity(): Flow<List<FavoriteEntity>>

    suspend fun existFavoriteEntity(contentId: String): Int
}