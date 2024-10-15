package kr.ksw.visitkorea.presentation.home.viewmodel

import androidx.compose.runtime.Immutable
import kr.ksw.visitkorea.domain.model.CommonCardModel
import kr.ksw.visitkorea.domain.model.Restaurant
import kr.ksw.visitkorea.domain.model.TouristSpot

@Immutable
data class HomeState(
    val mainImage: String = "",
    val touristSpotList: List<TouristSpot> = emptyList(),
    val cultureCenterList: List<CommonCardModel> = emptyList(),
    val leisureSportsList: List<CommonCardModel> = emptyList(),
    val restaurantList: List<Restaurant> = emptyList()
)