package kr.ksw.visitkorea.domain.usecase.detail

import kr.ksw.visitkorea.data.remote.dto.DetailInfoDTO
import kr.ksw.visitkorea.data.repository.DetailRepository
import javax.inject.Inject

class GetDetailInfoUseCaseImpl @Inject constructor(
    private val detailRepository: DetailRepository
) : GetDetailInfoUseCase {
    override suspend fun invoke(
        contentId: String
    ): Result<List<DetailInfoDTO>> {
        return detailRepository.getDetailInfo(
            contentId = contentId
        )
    }
}