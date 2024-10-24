package kr.ksw.visitkorea.domain.usecase.detail

import kr.ksw.visitkorea.data.remote.dto.DetailCommonDTO
import kr.ksw.visitkorea.data.repository.DetailRepository
import javax.inject.Inject

class GetDetailCommonUseCaseImpl @Inject constructor(
    private val detailRepository: DetailRepository
): GetDetailCommonUseCase {
    override suspend fun invoke(
        contentId: String
    ): Result<DetailCommonDTO> {
        return detailRepository.getDetailCommon(contentId)
    }
}