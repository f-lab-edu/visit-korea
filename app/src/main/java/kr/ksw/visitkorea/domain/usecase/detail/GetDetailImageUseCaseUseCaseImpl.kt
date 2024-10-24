package kr.ksw.visitkorea.domain.usecase.detail

import kr.ksw.visitkorea.data.remote.dto.DetailImageDTO
import kr.ksw.visitkorea.data.repository.DetailRepository
import javax.inject.Inject

class GetDetailImageUseCaseUseCaseImpl @Inject constructor(
    private val detailRepository: DetailRepository
): GetDetailImageUseCase {
    override suspend fun invoke(
        contentId: String,
        imageYN: String
    ): Result<List<DetailImageDTO>> {
        return detailRepository.getDetailImage(
            contentId = contentId,
            imageYN = imageYN
        )
    }
}