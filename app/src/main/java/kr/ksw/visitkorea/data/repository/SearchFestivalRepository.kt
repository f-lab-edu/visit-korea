package kr.ksw.visitkorea.data.repository

import kr.ksw.visitkorea.data.remote.dto.SearchFestivalDTO

interface SearchFestivalRepository {
    suspend operator fun invoke(
        numOfRows: Int,
        pageNo: Int,
        eventStartDate: String,
        eventEndDate: String,
        areaCode: String? = null,
        sigunguCode: String? = null
    ): Result<List<SearchFestivalDTO>>
}