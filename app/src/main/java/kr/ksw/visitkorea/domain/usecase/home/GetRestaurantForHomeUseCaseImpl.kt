package kr.ksw.visitkorea.domain.usecase.home

import kr.ksw.visitkorea.data.repository.LocationBasedListRepository
import kr.ksw.visitkorea.domain.common.TYPE_RESTAURANT
import kr.ksw.visitkorea.domain.usecase.mapper.toRestaurantModel
import kr.ksw.visitkorea.domain.model.Restaurant
import javax.inject.Inject

class GetRestaurantForHomeUseCaseImpl @Inject constructor(
    private val locationBasedListRepository: LocationBasedListRepository
) : GetRestaurantForHomeUseCase{
    override suspend fun invoke(
        mapX: String,
        mapY: String,
    ): Result<List<Restaurant>> {
        return locationBasedListRepository.getLocationBasedListByContentType(
            HOME_USE_CASE_DEFAULT_NUM_ROWS,
            HOME_USE_CASE_DEFAULT_PAGE,
            mapX,
            mapY,
            TYPE_RESTAURANT
        ).map { list ->
            list.map { dto ->
                dto.toRestaurantModel()
            }
        }
    }
}