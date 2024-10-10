package kr.ksw.visitkorea.data.repository

import kr.ksw.visitkorea.data.mapper.toItems
import kr.ksw.visitkorea.data.remote.api.LocationBasedListApi
import kr.ksw.visitkorea.data.remote.dto.LocationBasedDTO
import javax.inject.Inject

class LocationBasedListRepositoryImpl @Inject constructor(
    private val locationBasedListApi: LocationBasedListApi
): LocationBasedListRepository {
    override suspend fun getLocationBasedListByContentType(
        numOfRows: Int,
        pageNo: Int,
        mapX: String,
        mapY: String,
        contentTypeId: String,
    ): Result<List<LocationBasedDTO>> = runCatching {
        locationBasedListApi.getLocationBasedListByContentType(
            numOfRows = numOfRows,
            pageNo = pageNo,
            mapX = mapX,
            mapY = mapY,
            contentTypeId = contentTypeId
        ).toItems()
    }
}