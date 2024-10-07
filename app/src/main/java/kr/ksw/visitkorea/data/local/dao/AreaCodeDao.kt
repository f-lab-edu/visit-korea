package kr.ksw.visitkorea.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kr.ksw.visitkorea.data.local.entity.AreaCodeEntity
import kr.ksw.visitkorea.data.local.entity.SigunguCodeEntity

@Dao
interface AreaCodeDao {
    @Upsert
    suspend fun upsertAreaCodeEntity(areaCodeEntity: AreaCodeEntity)

    @Upsert
    suspend fun upsertSigunguCodeEntity(sigunguCodeEntity: SigunguCodeEntity)

    @Query("SELECT * FROM area_code")
    suspend fun getAllAreaCodeEntities(): List<AreaCodeEntity>

    @Query("SELECT * FROM sigungu_code WHERE areaCode = :areaCode")
    suspend fun getSigunguCodeByAreaCode(areaCode: String): List<SigunguCodeEntity>
}