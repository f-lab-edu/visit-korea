package kr.ksw.visitkorea.domain.usecase.search

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.ksw.visitkorea.data.remote.dto.LocationBasedDTO
import kr.ksw.visitkorea.data.repository.SearchKeywordRepository
import javax.inject.Inject

class GetListByKeywordUseCaseImpl @Inject constructor(
    private val searchKeywordRepository: SearchKeywordRepository
): GetListByKeywordUseCase {
    override suspend fun invoke(keyword: String): Result<Flow<PagingData<LocationBasedDTO>>> {
        return searchKeywordRepository.getListByKeyword(keyword)
    }
}