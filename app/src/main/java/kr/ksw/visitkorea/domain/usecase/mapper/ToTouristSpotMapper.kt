package kr.ksw.visitkorea.domain.usecase.mapper

import kr.ksw.visitkorea.data.remote.dto.LocationBasedDTO
import kr.ksw.visitkorea.domain.usecase.model.TouristSpot
import kr.ksw.visitkorea.domain.usecase.util.toDistForUi
import kr.ksw.visitkorea.domain.usecase.util.toImageUrl

fun LocationBasedDTO.toTouristSpotModel(): TouristSpot = TouristSpot(
    address,
    contentId,
    dist.toDistForUi(),
    firstImage.toImageUrl(),
    firstImage2.toImageUrl(),
    title
)