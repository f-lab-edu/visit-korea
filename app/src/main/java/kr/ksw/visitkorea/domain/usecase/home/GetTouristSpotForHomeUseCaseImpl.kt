package kr.ksw.visitkorea.domain.usecase.home

import kr.ksw.visitkorea.data.repository.LocationBasedListRepository
import kr.ksw.visitkorea.domain.common.TYPE_TOURIST_SPOT
import kr.ksw.visitkorea.domain.usecase.mapper.toTouristSpotModel
import kr.ksw.visitkorea.domain.model.TouristSpot
import javax.inject.Inject

class GetTouristSpotForHomeUseCaseImpl @Inject constructor(
    private val locationBasedListRepository: LocationBasedListRepository
): GetTouristSpotForHomeUseCase {
    override suspend fun invoke(
        mapX: String,
        mapY: String,
    ): Result<List<TouristSpot>> = runCatching {
        return locationBasedListRepository.getLocationBasedListByContentType(
            HOME_USE_CASE_DEFAULT_NUM_ROWS,
            HOME_USE_CASE_DEFAULT_PAGE,
            mapX,
            mapY,
            TYPE_TOURIST_SPOT
        ).map { list ->
            list.map { dto ->
                dto.toTouristSpotModel()
            }
        }
    }
}