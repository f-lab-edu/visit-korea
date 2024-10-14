package kr.ksw.visitkorea.data.paging.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kr.ksw.visitkorea.data.mapper.toItems
import kr.ksw.visitkorea.data.remote.api.LocationBasedListApi
import kr.ksw.visitkorea.data.remote.dto.LocationBasedDTO

class LocationBasedPagingSource (
    private val locationBasedListApi: LocationBasedListApi,
    private val contentTypeId: String,
    private val mapX: String,
    private val mapY: String
) : PagingSource<Int, LocationBasedDTO>() {

    override fun getRefreshKey(state: PagingState<Int, LocationBasedDTO>): Int? {
        return state.anchorPosition?.let { anchor ->
            val anchorPage = state.closestPageToPosition(anchor)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocationBasedDTO> {
        val page = params.key ?: 1
        val loadSize = params.loadSize
        val response = locationBasedListApi.getLocationBasedListByContentType(
            numOfRows = loadSize,
            pageNo =  page,
            mapX = mapX,
            mapY = mapY,
            contentTypeId = contentTypeId
        )
        val data = response.toItems()
        return LoadResult.Page(
            data = data,
            prevKey = if(page == 1) null else page - 1,
            nextKey = if(data.size == loadSize) page + 1 else null
        )
    }

}