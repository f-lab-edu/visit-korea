package kr.ksw.visitkorea.domain.usecase.home

import kr.ksw.visitkorea.domain.usecase.model.LeisureSports

interface GetLeisureSportsForHomeUseCase {
    suspend operator fun invoke(
        mapX: String,
        mapY: String,
    ): Result<List<LeisureSports>>
}