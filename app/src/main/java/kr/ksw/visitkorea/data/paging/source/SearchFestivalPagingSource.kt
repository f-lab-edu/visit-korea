package kr.ksw.visitkorea.data.paging.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kr.ksw.visitkorea.data.mapper.toItems
import kr.ksw.visitkorea.data.remote.api.SearchFestivalApi
import kr.ksw.visitkorea.data.remote.dto.SearchFestivalDTO

class SearchFestivalPagingSource(
    private val searchFestivalApi: SearchFestivalApi,
    private val eventStartDate: String,
    private val eventEndDate: String,
    private val areaCode: String?,
    private val sigunguCode: String?
) : PagingSource<Int, SearchFestivalDTO>() {

    override fun getRefreshKey(state: PagingState<Int, SearchFestivalDTO>): Int? {
        return state.anchorPosition?.let { anchor ->
            val anchorPage = state.closestPageToPosition(anchor)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchFestivalDTO> {
        val page = params.key ?: 1
        val loadSize = params.loadSize
        val response = searchFestivalApi.searchFestival(
            numOfRows = loadSize,
            pageNo =  page,
            eventStartDate = eventStartDate,
            eventEndDate = eventEndDate,
            areaCode = areaCode,
            sigunguCode = sigunguCode
        )
        val data = response.toItems()
        return LoadResult.Page(
            data = data,
            prevKey = if(page == 1) null else page - 1,
            nextKey = if(data.size == loadSize && page * loadSize < 50)
                page + 1
            else null
        )
    }
}