package kr.ksw.visitkorea.domain.usecase.home

import kr.ksw.visitkorea.data.repository.LocationBasedListRepository
import kr.ksw.visitkorea.domain.usecase.mapper.toRestaurantModel
import kr.ksw.visitkorea.domain.usecase.model.Restaurant
import javax.inject.Inject

class GetRestaurantForHomeUseCaseImpl @Inject constructor(
    private val locationBasedListRepository: LocationBasedListRepository
) : GetRestaurantForHomeUseCase{
    override suspend fun invoke(
        mapX: String,
        mapY: String,
    ): Result<List<Restaurant>> {
        return locationBasedListRepository.getLocationBasedListByContentType(
            10, 1, mapX, mapY, "39"
        ).map { list ->
            list.map { dto ->
                dto.toRestaurantModel()
            }
        }
    }
}