package kr.ksw.visitkorea.domain.usecase.home

import kr.ksw.visitkorea.data.repository.LocationBasedListRepository
import kr.ksw.visitkorea.domain.common.TYPE_CULTURE
import kr.ksw.visitkorea.domain.usecase.mapper.toCommonCardModel
import kr.ksw.visitkorea.domain.model.CommonCardModel
import javax.inject.Inject

class GetCultureCenterForHomeUseCaseImpl @Inject constructor(
    private val locationBasedListRepository: LocationBasedListRepository
) : GetCultureCenterForHomeUseCase {
    override suspend fun invoke(
        mapX: String,
        mapY: String
    ): Result<List<CommonCardModel>> {
        return locationBasedListRepository.getLocationBasedListByContentType(
            HOME_USE_CASE_DEFAULT_NUM_ROWS,
            HOME_USE_CASE_DEFAULT_PAGE,
            mapX,
            mapY,
            TYPE_CULTURE
        ).map { list ->
            list.map { dto ->
                dto.toCommonCardModel()
            }
        }
    }
}