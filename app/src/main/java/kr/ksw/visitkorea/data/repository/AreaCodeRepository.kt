package kr.ksw.visitkorea.data.repository

import kr.ksw.visitkorea.data.local.entity.AreaCodeEntity
import kr.ksw.visitkorea.data.remote.dto.AreaCodeDTO

interface AreaCodeRepository {
    suspend fun getAreaCode(): Result<List<AreaCodeDTO>>

    suspend fun getSigunguCode(areaCode: String): Result<List<AreaCodeDTO>>

    suspend fun getAllAreaCodes(): List<AreaCodeEntity>
}