package kr.ksw.visitkorea.data.local.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import kr.ksw.visitkorea.data.local.dao.FavoriteDao
import kr.ksw.visitkorea.data.local.entity.FavoriteEntity

@Database(
    entities = [FavoriteEntity::class],
    version = 1
)
abstract class FavoriteDatabase: RoomDatabase() {
    abstract val favoriteDao: FavoriteDao
}