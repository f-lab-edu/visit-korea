package kr.ksw.visitkorea.domain.usecase.festival

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import kr.ksw.visitkorea.data.paging.source.SearchFestivalPagingSource
import kr.ksw.visitkorea.data.remote.api.SearchFestivalApi
import kr.ksw.visitkorea.data.remote.dto.SearchFestivalDTO
import javax.inject.Inject

class GetFestivalListUseCaseImpl @Inject constructor(
    private val searchFestivalApi: SearchFestivalApi
) : GetFestivalListUseCase {
    private var pagingSource: PagingSource<Int, SearchFestivalDTO>? = null

    override suspend fun invoke(
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
                pageSize = 20,
                initialLoadSize = 20
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