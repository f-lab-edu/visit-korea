package kr.ksw.visitkorea.domain.usecase.home

import kr.ksw.visitkorea.domain.usecase.model.CultureCenter

interface GetCultureCenterForHomeUseCase {
    suspend operator fun invoke(
        mapX: String,
        mapY: String,
    ): Result<List<CultureCenter>>
}