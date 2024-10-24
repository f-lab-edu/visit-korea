package kr.ksw.visitkorea.data.paging.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kr.ksw.visitkorea.data.mapper.toItems
import kr.ksw.visitkorea.data.remote.api.SearchKeywordApi
import kr.ksw.visitkorea.data.remote.dto.LocationBasedDTO

class SearchKeyWordPagingSource(
    private val searchKeywordApi: SearchKeywordApi,
    private val keyword: String
) : PagingSource<Int, LocationBasedDTO>(){

    override fun getRefreshKey(state: PagingState<Int, LocationBasedDTO>): Int? {
        return state.anchorPosition?.let { anchor ->
            val anchorPage = state.closestPageToPosition(anchor)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocationBasedDTO> {
        val page = params.key ?: 1
        val loadSize = params.loadSize
        val data = try {
            searchKeywordApi.searchListByKeyword(
                numOfRows = loadSize,
                pageNo = page,
                keyword = keyword
            ).toItems()
        } catch (e: Exception) {
            emptyList()
        }
        return LoadResult.Page(
            data = data,
            prevKey = if(page == 1 || data.isEmpty()) null else page - 1,
            nextKey = if(data.size == loadSize && data.isNotEmpty()) page + 1 else null
        )
    }

}