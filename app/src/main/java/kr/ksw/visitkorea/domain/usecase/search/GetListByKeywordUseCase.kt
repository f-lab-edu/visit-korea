package kr.ksw.visitkorea.domain.usecase.search

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.ksw.visitkorea.data.remote.dto.LocationBasedDTO

interface GetListByKeywordUseCase {
    suspend operator fun invoke(
        keyword: String
    ): Result<Flow<PagingData<LocationBasedDTO>>>
}