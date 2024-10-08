package kr.ksw.visitkorea.domain.usecase.detail

import kr.ksw.visitkorea.data.remote.dto.DetailImageDTO

interface GetDetailImageUseCase {
    suspend operator fun invoke(
        contentId: String,
        imageYN: String
    ): Result<List<DetailImageDTO>>
}