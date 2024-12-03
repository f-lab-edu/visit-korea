package kr.ksw.visitkorea.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import kr.ksw.visitkorea.data.paging.source.SearchFestivalPagingSource
import kr.ksw.visitkorea.data.remote.api.SearchFestivalApi
import kr.ksw.visitkorea.data.remote.dto.SearchFestivalDTO
import javax.inject.Inject

class SearchFestivalRepositoryImpl @Inject constructor(
    private val searchFestivalApi: SearchFestivalApi
): SearchFestivalRepository {
    private var pagingSource: PagingSource<Int, SearchFestivalDTO>? = null

    override suspend fun searchFestival(
        forceFetch: Boolean,
        eventStartDate: String,
        eventEndDate: String,
        areaCode: String?,
        sigunguCode: String?
    ): Result<Flow<PagingData<SearchFestivalDTO>>> = runCatching {
        if(forceFetch &&
            pagingSource != null &&
            pagingSource?.invalid != true) {
            pagingSource?.invalidate()
        }
        Pager(
            config = PagingConfig(
                pageSize = 40,
                initialLoadSize = 40
            ),
            pagingSourceFactory = {
                SearchFestivalPagingSource(
                    searchFestivalApi,
                    eventStartDate,
                    eventEndDate,
                    areaCode,
                    sigunguCode
                ).also {
                    pagingSource = it
                }
            }
        ).flow
    }
}