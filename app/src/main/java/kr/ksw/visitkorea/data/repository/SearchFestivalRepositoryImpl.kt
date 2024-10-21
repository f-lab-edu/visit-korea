package kr.ksw.visitkorea.data.repository

import kr.ksw.visitkorea.data.mapper.toItems
import kr.ksw.visitkorea.data.remote.api.SearchFestivalApi
import kr.ksw.visitkorea.data.remote.dto.SearchFestivalDTO
import javax.inject.Inject

class SearchFestivalRepositoryImpl @Inject constructor(
    private val searchFestivalApi: SearchFestivalApi
): SearchFestivalRepository {
    override suspend fun searchFestival(
        numOfRows: Int,
        pageNo: Int,
        eventStartDate: String,
        eventEndDate: String,
        areaCode: String?,
        sigunguCode: String?
    ): Result<List<SearchFestivalDTO>> = runCatching {
        searchFestivalApi.searchFestival(
            numOfRows = numOfRows,
            pageNo = pageNo,
            eventStartDate = eventStartDate,
            eventEndDate = eventEndDate,
            areaCode = areaCode,
            sigunguCode = sigunguCode
        ).toItems()
    }
}