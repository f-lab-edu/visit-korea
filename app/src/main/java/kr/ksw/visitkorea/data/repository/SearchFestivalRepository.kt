package kr.ksw.visitkorea.data.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.ksw.visitkorea.data.remote.dto.SearchFestivalDTO

interface SearchFestivalRepository {
    suspend fun searchFestival(
        forceFetch: Boolean,
        eventStartDate: String,
        eventEndDate: String,
        areaCode: String? = null,
        sigunguCode: String? = null,
    ): Result<Flow<PagingData<SearchFestivalDTO>>>
}