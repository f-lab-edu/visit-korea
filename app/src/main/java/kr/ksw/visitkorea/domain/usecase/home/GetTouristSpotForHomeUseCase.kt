package kr.ksw.visitkorea.domain.usecase.home

import kr.ksw.visitkorea.domain.model.TouristSpot

interface GetTouristSpotForHomeUseCase {
    suspend operator fun invoke(
        mapX: String,
        mapY: String,
    ): Result<List<TouristSpot>>
}