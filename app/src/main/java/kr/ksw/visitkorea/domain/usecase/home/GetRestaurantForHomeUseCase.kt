package kr.ksw.visitkorea.domain.usecase.home

import kr.ksw.visitkorea.domain.usecase.model.Restaurant

interface GetRestaurantForHomeUseCase {
    suspend operator fun invoke(
        mapX: String,
        mapY: String
    ) : Result<List<Restaurant>>
}