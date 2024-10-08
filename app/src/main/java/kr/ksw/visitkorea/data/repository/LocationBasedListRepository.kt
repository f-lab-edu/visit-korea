package kr.ksw.visitkorea.data.repository

import kr.ksw.visitkorea.data.remote.dto.LocationBasedDTO

interface LocationBasedListRepository {
    suspend fun getLocationBasedListByContentType(
        numOfRows: Int,
        pageNo: Int,
        mapX: String,
        mapY: String,
        contentTypeId: String
    ): Result<List<LocationBasedDTO>>
}