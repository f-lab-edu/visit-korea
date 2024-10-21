package kr.ksw.visitkorea.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import kr.ksw.visitkorea.data.paging.source.SearchKeyWordPagingSource
import kr.ksw.visitkorea.data.remote.api.SearchKeywordApi
import kr.ksw.visitkorea.data.remote.dto.LocationBasedDTO
import javax.inject.Inject

class SearchKeywordRepositoryImpl @Inject constructor(
    private val searchKeywordApi: SearchKeywordApi
) : SearchKeywordRepository {
    private var pagingSource: PagingSource<Int, LocationBasedDTO>? = null

    override suspend fun getListByKeyword(
        keyword: String
    ): Result<Flow<PagingData<LocationBasedDTO>>> = runCatching {
        pagingSource?.run {
            if(!invalid) {
                invalidate()
            }
        }
        Pager(
            config = PagingConfig(
                pageSize = 30,
                initialLoadSize = 30
            ),
            pagingSourceFactory = {
                SearchKeyWordPagingSource(
                    searchKeywordApi,
                    keyword,
                ).also {
                    pagingSource = it
                }
            }
        ).flow
    }
}