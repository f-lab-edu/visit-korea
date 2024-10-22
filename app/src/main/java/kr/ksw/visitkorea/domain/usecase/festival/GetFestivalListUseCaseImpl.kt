package kr.ksw.visitkorea.domain.usecase.festival

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.ksw.visitkorea.data.remote.dto.SearchFestivalDTO
import kr.ksw.visitkorea.data.repository.SearchFestivalRepository
import javax.inject.Inject

class GetFestivalListUseCaseImpl @Inject constructor(
    private val searchFestivalRepository: SearchFestivalRepository
) : GetFestivalListUseCase {
    override suspend fun invoke(
        forceFetch: Boolean,
        eventStartDate: String,
        eventEndDate: String,
        areaCode: String?,
        sigunguCode: String?
    ): Result<Flow<PagingData<SearchFestivalDTO>>> {
        return searchFestivalRepository.searchFestival(
            forceFetch = forceFetch,
            eventStartDate = eventStartDate,
            eventEndDate = eventEndDate
        )
    }
}