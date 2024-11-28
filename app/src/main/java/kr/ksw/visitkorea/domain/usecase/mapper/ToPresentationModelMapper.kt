package kr.ksw.visitkorea.domain.usecase.mapper

import kr.ksw.visitkorea.data.remote.dto.DetailInfoDTO
import kr.ksw.visitkorea.data.remote.dto.DetailIntroDTO
import kr.ksw.visitkorea.data.remote.dto.LocationBasedDTO
import kr.ksw.visitkorea.data.remote.dto.SearchFestivalDTO
import kr.ksw.visitkorea.domain.common.TYPE_CULTURE
import kr.ksw.visitkorea.domain.common.TYPE_LEiSURE
import kr.ksw.visitkorea.domain.common.TYPE_RESTAURANT
import kr.ksw.visitkorea.domain.common.TYPE_TOURIST_SPOT
import kr.ksw.visitkorea.domain.usecase.model.CommonCardModel
import kr.ksw.visitkorea.domain.usecase.model.CommonDetail
import kr.ksw.visitkorea.domain.usecase.model.Festival
import kr.ksw.visitkorea.domain.usecase.model.HotelDetail
import kr.ksw.visitkorea.domain.usecase.model.HotelRoomDetail
import kr.ksw.visitkorea.domain.usecase.model.MoreCardModel
import kr.ksw.visitkorea.domain.usecase.model.Restaurant
import kr.ksw.visitkorea.domain.usecase.model.TouristSpot
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
    dist,
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
    category = restaurantMap[category] ?: "",
    contentId = contentId
)

fun LocationBasedDTO.toMoreCardModel(): MoreCardModel = MoreCardModel(
    address,
    firstImage.toImageUrl(),
    title,
    dist.toDistForUi(),
    contentId,
    restaurantMap[category]
)

fun DetailIntroDTO.toCommonDetail(contentTypeId: String): CommonDetail = when(contentTypeId) {
    TYPE_TOURIST_SPOT -> CommonDetail(
        parking = parking,
        time = useTime,
        restDate = restDate
    )
    TYPE_CULTURE -> CommonDetail(
        parking = parkingCulture,
        time = useTimeCulture,
        restDate = restDateCulture,
        fee = useFee
    )
    TYPE_LEiSURE -> CommonDetail(
        parking = parkingLeports,
        time = useTimeLeports,
        restDate = restDateLeports,
        fee = useFeeLeports
    )
    TYPE_RESTAURANT -> CommonDetail(
        parking = parkingFood,
        time = openTimeFood,
        restDate = restDateFood,
        menus = firstMenu
    )
    else -> CommonDetail(
        time = playTime,
        fee = useTimeFestival,
        place = eventPlace
    )
}

fun DetailIntroDTO.toHotelDetail(): HotelDetail = HotelDetail(
    checkInTime = checkInTime,
    checkOutTime = checkOutTime,
    subFacility = subFacility,
    reservationUrl = reservationUrl,
    tel = infoCenterLodging
)

fun DetailInfoDTO.toHotelRoomDetail(): HotelRoomDetail {
    return HotelRoomDetail(
        roomTitle = roomTitle,
        roomSize = roomSize,
        roomSize2 = roomSize2,
        roomBaseCount = roomBaseCount,
        roomMaxCount = roomMaxCount,
        roomOffSeasonMinFee1 = if(roomOffSeasonMinFee1.isEmpty() || roomOffSeasonMinFee1 == "0")
            roomPeakSeasonMinFee1 else roomOffSeasonMinFee1,
        roomOffSeasonMinFee2 = if(roomOffSeasonMinFee2.isEmpty() || roomOffSeasonMinFee2 == "0")
            roomPeakSeasonMinFee2 else roomOffSeasonMinFee2,
        roomBathFacility = roomBathFacility,
        roomTv = roomTv,
        roomPc = roomPc,
        roomInternet = roomInternet,
        roomRefrigerator = roomRefrigerator,
        roomHairdryer = roomHairdryer,
        roomImages = filterImages(
            roomImg1,
            roomImg2,
            roomImg3,
            roomImg4,
            roomImg5
        )
    )
}

fun filterImages(vararg images: String) = images.asList().filterNot { it.isEmpty() }