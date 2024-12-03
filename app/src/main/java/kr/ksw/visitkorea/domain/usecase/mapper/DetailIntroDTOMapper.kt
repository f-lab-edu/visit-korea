package kr.ksw.visitkorea.domain.usecase.mapper

import kr.ksw.visitkorea.data.remote.dto.DetailInfoDTO
import kr.ksw.visitkorea.data.remote.dto.DetailIntroDTO
import kr.ksw.visitkorea.domain.common.TYPE_CULTURE
import kr.ksw.visitkorea.domain.common.TYPE_LEiSURE
import kr.ksw.visitkorea.domain.common.TYPE_RESTAURANT
import kr.ksw.visitkorea.domain.common.TYPE_TOURIST_SPOT
import kr.ksw.visitkorea.domain.model.CommonDetail
import kr.ksw.visitkorea.domain.model.HotelDetail
import kr.ksw.visitkorea.domain.model.HotelRoomDetail
import kr.ksw.visitkorea.domain.usecase.util.toImageUrl

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
    reservationUrl = reservationUrl ?: reservationLodging,
    tel = infoCenterLodging
)

fun DetailInfoDTO.toHotelRoomDetail(): HotelRoomDetail {
    val images = listOf(roomImg1, roomImg2, roomImg3, roomImg4, roomImg5).filterNot {
        it.isEmpty()
    }.map {
        it.toImageUrl()
    }
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
        roomImages = images
    )
}