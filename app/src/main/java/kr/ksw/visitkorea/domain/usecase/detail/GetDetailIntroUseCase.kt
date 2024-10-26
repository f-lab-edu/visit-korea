package kr.ksw.visitkorea.domain.usecase.detail

import kr.ksw.visitkorea.domain.usecase.model.CommonDetail

interface GetDetailIntroUseCase {
    suspend operator fun invoke(
        contentId: String,
        contentTypeId: String
    ): Result<CommonDetail>
}