package kr.ksw.visitkorea.presentation.hotel.viewmodel

import androidx.compose.runtime.Immutable
import kr.ksw.visitkorea.domain.usecase.model.CommonCardModel

@Immutable
data class HotelState(
    val hotelList: List<CommonCardModel>
)