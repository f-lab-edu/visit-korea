package kr.ksw.visitkorea.domain.usecase.mapper

import kr.ksw.visitkorea.data.remote.dto.LocationBasedDTO
import kr.ksw.visitkorea.data.remote.dto.SearchFestivalDTO
import kr.ksw.visitkorea.domain.model.CommonCardModel
import kr.ksw.visitkorea.domain.model.Festival
import kr.ksw.visitkorea.domain.model.MoreCardModel
import kr.ksw.visitkorea.domain.model.Restaurant
import kr.ksw.visitkorea.domain.model.TouristSpot
import kr.ksw.visitkorea.domain.usecase.util.toDateString
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

fun LocationBasedDTO.toCommonCardModel(): CommonCardModel = CommonCardModel(
    address,
    firstImage.toImageUrl(),
    title,
    dist.toDistForUi(),
    contentId,
    contentTypeId
)

fun SearchFestivalDTO.toFestival(): Festival = Festival(
    address,
    firstImage.toImageUrl(),
    title,
    contentId,
    eventStartDate.toDateString(),
    eventEndDate.toDateString()
)

val restaurantMap = mapOf(
    "A05020100" to "한식",
    "A05020200" to "서양식",
    "A05020300" to "일식",
    "A05020400" to "중식",
    "A05020700" to "이색음식점",
    "A05020900" to "카페",
    "A05021000" to "클럽",
)

fun LocationBasedDTO.toRestaurantModel(): Restaurant = Restaurant(
    address = address,
    dist = dist.toDistForUi(),
    firstImage = firstImage.toImageUrl(),
    firstImage2 = firstImage2.toImageUrl(),
    title = title,
    category = restaurantMap[cat3] ?: "",
    contentId = contentId
)

fun LocationBasedDTO.toMoreCardModel(): MoreCardModel = MoreCardModel(
    address,
    firstImage.toImageUrl(),
    title,
    dist.toDistForUi(),
    contentId,
    restaurantMap[cat3]
)