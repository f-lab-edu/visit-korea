package kr.ksw.visitkorea.domain.usecase.detail

import kr.ksw.visitkorea.data.remote.dto.DetailCommonDTO

interface GetDetailCommonUseCase {
    suspend operator fun invoke(
        contentId: String
    ): Result<DetailCommonDTO>
}