package kr.ksw.visitkorea.domain.usecase.home

import kr.ksw.visitkorea.data.repository.LocationBasedListRepository
import kr.ksw.visitkorea.domain.usecase.mapper.toLeisureSportsModel
import kr.ksw.visitkorea.domain.usecase.model.LeisureSports
import javax.inject.Inject

class GetLeisureSportsForHomeUseCaseImpl @Inject constructor(
    private val locationBasedListRepository: LocationBasedListRepository
): GetLeisureSportsForHomeUseCase {
    override suspend fun invoke(
        mapX: String,
        mapY: String
    ): Result<List<LeisureSports>> {
        return locationBasedListRepository.getLocationBasedListByContentType(
            10, 1, mapX, mapY, "28"
        ).map { list ->
            list.map { dto ->
                dto.toLeisureSportsModel()
            }
        }
    }
}