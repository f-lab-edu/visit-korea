package kr.ksw.visitkorea.domain.usecase.more

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import kr.ksw.visitkorea.data.paging.source.LocationBasedPagingSource
import kr.ksw.visitkorea.data.remote.api.LocationBasedListApi
import kr.ksw.visitkorea.data.remote.dto.LocationBasedDTO
import javax.inject.Inject

class GetMoreListUseCaseImpl @Inject constructor(
    private val locationBasedListApi: LocationBasedListApi
): GetMoreListUseCase {
    private var pagingSource: PagingSource<Int, LocationBasedDTO>? = null

    override suspend fun invoke(
        forceFetch: Boolean,
        mapX: String,
        mapY: String,
        contentTypeId: String
    ): Result<Flow<PagingData<LocationBasedDTO>>> = runCatching {
        if(forceFetch &&
            pagingSource != null &&
            pagingSource?.invalid != true) {
            pagingSource?.invalidate()
        }
        Pager(
            config = PagingConfig(
                pageSize = 50,
                initialLoadSize = 50
            ),
            pagingSourceFactory = {
                LocationBasedPagingSource(
                    locationBasedListApi,
                    contentTypeId,
                    mapX,
                    mapY
                ).also {
                    pagingSource = it
                }
            }
        ).flow
    }
}