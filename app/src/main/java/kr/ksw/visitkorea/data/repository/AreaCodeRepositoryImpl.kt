package kr.ksw.visitkorea.data.repository

import kr.ksw.visitkorea.data.local.dao.AreaCodeDao
import kr.ksw.visitkorea.data.local.entity.AreaCodeEntity
import kr.ksw.visitkorea.data.mapper.toItems
import kr.ksw.visitkorea.data.remote.api.AreaCodeApi
import kr.ksw.visitkorea.data.remote.dto.AreaCodeDTO
import javax.inject.Inject

class AreaCodeRepositoryImpl @Inject constructor(
    private val areaCodeApi: AreaCodeApi,
    private val areaCodeDao: AreaCodeDao
): AreaCodeRepository {
    override suspend fun getAreaCode(): Result<List<AreaCodeDTO>> = runCatching {
        areaCodeApi.getAreaCode().toItems()
    }

    override suspend fun getSigunguCode(areaCode: String): Result<List<AreaCodeDTO>> = runCatching {
        areaCodeApi.getSigunguCode(areaCode = areaCode).toItems()
    }

    override suspend fun getAllAreaCodes(): List<AreaCodeEntity> {
        return areaCodeDao.getAllAreaCodeEntities()
    }
}