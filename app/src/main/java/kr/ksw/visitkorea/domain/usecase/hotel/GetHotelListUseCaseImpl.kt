package kr.ksw.visitkorea.domain.usecase.hotel

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import kr.ksw.visitkorea.data.paging.source.LocationBasedPagingSource
import kr.ksw.visitkorea.data.remote.api.LocationBasedListApi
import kr.ksw.visitkorea.data.remote.dto.LocationBasedDTO
import javax.inject.Inject

class GetHotelListUseCaseImpl @Inject constructor(
    private val locationBasedListApi: LocationBasedListApi
): GetHotelListUseCase {
    private var pagingSource: PagingSource<Int, LocationBasedDTO>? = null

    override suspend fun invoke(
        forceFetch: Boolean,
        mapX: String,
        mapY: String
    ): Result<Flow<PagingData<LocationBasedDTO>>> = runCatching {
        if(forceFetch &&
            pagingSource != null &&
            pagingSource?.invalid != true) {
            pagingSource?.invalidate()
        }
        Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = {
                LocationBasedPagingSource(
                    locationBasedListApi,
                    "32",
                    mapX,
                    mapY
                ).also {
                    pagingSource = it
                }
            }
        ).flow
    }
}