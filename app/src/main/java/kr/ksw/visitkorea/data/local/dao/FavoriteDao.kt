package kr.ksw.visitkorea.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import kr.ksw.visitkorea.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Upsert
    suspend fun upsertFavoriteEntity(favoriteEntity: FavoriteEntity)

    @Delete
    suspend fun deleteFavoriteEntity(favoriteEntity: FavoriteEntity)

    @Query("DELETE FROM favorite_table WHERE contentId = :contentId")
    suspend fun deleteFavoriteEntityByContentId(contentId: String)

    @Query("SELECT * FROM favorite_table")
    fun getAllFavoriteEntity(): Flow<List<FavoriteEntity>>

    @Query("SELECT EXISTS (SELECT * FROM favorite_table WHERE contentId = :contentId)")
    suspend fun existFavoriteEntity(contentId: String): Int
}