package kr.ksw.visitkorea.domain.usecase.hotel

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.ksw.visitkorea.data.remote.dto.LocationBasedDTO

interface GetHotelListUseCase {
    suspend operator fun invoke(
        forceFetch: Boolean = false,
        mapX: String,
        mapY: String
    ): Result<Flow<PagingData<LocationBasedDTO>>>
}