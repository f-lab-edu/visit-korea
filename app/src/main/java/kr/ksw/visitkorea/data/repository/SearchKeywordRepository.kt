package kr.ksw.visitkorea.data.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.ksw.visitkorea.data.remote.dto.LocationBasedDTO

interface SearchKeywordRepository {
    suspend fun getListByKeyword(
        keyword: String
    ): Result<Flow<PagingData<LocationBasedDTO>>>
}