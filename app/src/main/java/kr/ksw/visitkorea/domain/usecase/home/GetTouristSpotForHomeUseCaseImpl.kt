package kr.ksw.visitkorea.domain.usecase.home

import kr.ksw.visitkorea.data.repository.LocationBasedListRepository
import kr.ksw.visitkorea.domain.usecase.mapper.toTouristSpotModel
import kr.ksw.visitkorea.domain.usecase.model.TouristSpot
import javax.inject.Inject

class GetTouristSpotForHomeUseCaseImpl @Inject constructor(
    private val locationBasedListRepository: LocationBasedListRepository
): GetTouristSpotForHomeUseCase {
    override suspend fun invoke(mapX: String, mapY: String): Result<List<TouristSpot>> = runCatching {
        return locationBasedListRepository.getLocationBasedListByContentType(
            10, 1, mapX, mapY, "12"
        ).map { list ->
            list.map { dto ->
                dto.toTouristSpotModel()
            }
        }
    }
}