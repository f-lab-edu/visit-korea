package kr.ksw.visitkorea.domain.usecase.festival

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.ksw.visitkorea.data.remote.dto.SearchFestivalDTO

interface GetFestivalListUseCase {
    suspend operator fun invoke(
        forceFetch: Boolean,
        eventStartDate: String,
        eventEndDate: String,
        areaCode: String?,
        sigunguCode: String?
    ) : Result<Flow<PagingData<SearchFestivalDTO>>>
}