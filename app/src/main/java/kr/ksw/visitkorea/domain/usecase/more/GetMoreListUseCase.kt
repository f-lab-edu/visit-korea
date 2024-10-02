package kr.ksw.visitkorea.domain.usecase.more

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.ksw.visitkorea.data.remote.dto.LocationBasedDTO

interface GetMoreListUseCase {
    suspend operator fun invoke(
        forceFetch: Boolean,
        mapX: String,
        mapY: String,
        contentTypeId: String
    ) : Result<Flow<PagingData<LocationBasedDTO>>>
}