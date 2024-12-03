package kr.ksw.visitkorea.presentation.detail.viewmodel

import kr.ksw.visitkorea.data.remote.dto.DetailImageDTO
import kr.ksw.visitkorea.domain.model.HotelDetail
import kr.ksw.visitkorea.domain.model.HotelRoomDetail

data class DetailHotelState (
    val title: String = "",
    val firstImage: String = "",
    val address: String = "",
    val dist: String? = null,
    val homePage: String = "",
    val lat: String = "",
    val lng: String = "",
    val showFacilityInfo: Boolean = true,
    val showRoomDetail: Boolean = false,
    val hotelDetail: HotelDetail = HotelDetail(),
    val hotelRoomDetail: List<HotelRoomDetail> = emptyList(),
    val images: List<DetailImageDTO> = emptyList(),
    val viewPagerOpen: Boolean = false,
    val selectedImage: Int = 0,
    val viewPagerImages: List<String> = emptyList(),
)