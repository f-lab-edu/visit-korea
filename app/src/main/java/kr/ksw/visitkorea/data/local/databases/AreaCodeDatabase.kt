package kr.ksw.visitkorea.data.local.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import kr.ksw.visitkorea.data.local.dao.AreaCodeDao
import kr.ksw.visitkorea.data.local.entity.AreaCodeEntity
import kr.ksw.visitkorea.data.local.entity.SigunguCodeEntity

@Database(
    entities = [AreaCodeEntity::class, SigunguCodeEntity::class],
    version = 1
)
abstract class AreaCodeDatabase: RoomDatabase() {
    abstract val areaCodeDao: AreaCodeDao
}