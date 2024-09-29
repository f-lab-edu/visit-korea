package kr.ksw.visitkorea.domain.usecase.home

import kr.ksw.visitkorea.data.repository.LocationBasedListRepository
import kr.ksw.visitkorea.domain.usecase.mapper.toCultureCenterModel
import kr.ksw.visitkorea.domain.usecase.model.CultureCenter
import javax.inject.Inject

class GetCultureCenterForHomeUseCaseImpl @Inject constructor(
    private val locationBasedListRepository: LocationBasedListRepository
) : GetCultureCenterForHomeUseCase {
    override suspend fun invoke(
        mapX: String,
        mapY: String
    ): Result<List<CultureCenter>> {
        return locationBasedListRepository.getLocationBasedListByContentType(
            10, 1, mapX, mapY, "14"
        ).map { list ->
            list.map { dto ->
                dto.toCultureCenterModel()
            }
        }
    }
}