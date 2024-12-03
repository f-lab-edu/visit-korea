package kr.ksw.visitkorea.domain.model

data class HotelRoomDetail(
    val roomTitle: String = "",
    val roomSize: String = "",
    val roomSize2: String = "",
    val roomBaseCount: String = "",
    val roomMaxCount: String = "",
    val roomOffSeasonMinFee1: String = "",
    val roomOffSeasonMinFee2: String = "",
    val roomBathFacility: String = "",
    val roomTv: String = "",
    val roomPc: String = "",
    val roomInternet: String = "",
    val roomRefrigerator: String = "",
    val roomHairdryer: String = "",
    val roomImages: List<String> = emptyList(),
)