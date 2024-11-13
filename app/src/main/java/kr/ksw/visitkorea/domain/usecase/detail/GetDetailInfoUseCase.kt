package kr.ksw.visitkorea.domain.usecase.detail

import kr.ksw.visitkorea.data.remote.dto.DetailInfoDTO

interface GetDetailInfoUseCase {
    suspend operator fun invoke(
        contentId: String
    ): Result<List<DetailInfoDTO>>
}