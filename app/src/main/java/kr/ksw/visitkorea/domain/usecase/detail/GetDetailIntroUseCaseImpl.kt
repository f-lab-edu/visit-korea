package kr.ksw.visitkorea.domain.usecase.detail

import kr.ksw.visitkorea.data.repository.DetailRepository
import kr.ksw.visitkorea.domain.usecase.mapper.toCommonDetail
import kr.ksw.visitkorea.domain.model.CommonDetail
import javax.inject.Inject

class GetDetailIntroUseCaseImpl @Inject constructor(
    private val detailRepository: DetailRepository
): GetDetailIntroUseCase {
    override suspend fun invoke(
        contentId: String,
        contentTypeId: String
    ): Result<CommonDetail> = runCatching {
        detailRepository.getDetailIntro(
            contentId,
            contentTypeId
        ).getOrThrow().toCommonDetail(contentTypeId)
    }
}