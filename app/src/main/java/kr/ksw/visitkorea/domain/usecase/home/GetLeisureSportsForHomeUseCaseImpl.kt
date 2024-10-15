package kr.ksw.visitkorea.domain.usecase.home

import kr.ksw.visitkorea.data.repository.LocationBasedListRepository
import kr.ksw.visitkorea.domain.usecase.mapper.toCommonCardModel
import kr.ksw.visitkorea.domain.model.CommonCardModel
import javax.inject.Inject

class GetLeisureSportsForHomeUseCaseImpl @Inject constructor(
    private val locationBasedListRepository: LocationBasedListRepository
): GetLeisureSportsForHomeUseCase {
    override suspend fun invoke(
        mapX: String,
        mapY: String
    ): Result<List<CommonCardModel>> {
        return locationBasedListRepository.getLocationBasedListByContentType(
            10, 1, mapX, mapY, "28"
        ).map { list ->
            list.map { dto ->
                dto.toCommonCardModel()
            }
        }
    }
}