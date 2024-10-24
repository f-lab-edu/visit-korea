package kr.ksw.visitkorea.presentation.home.viewmodel

import androidx.compose.runtime.Immutable
import kr.ksw.visitkorea.domain.model.CommonCardModel
import kr.ksw.visitkorea.domain.model.Restaurant
import kr.ksw.visitkorea.domain.model.TouristSpot

@Immutable
data class HomeState(
    val mainPagerItems: List<HomePagerItem> = emptyList(),
    val touristSpotComplete: Boolean = false,
    val touristSpotList: List<TouristSpot> = emptyList(),
    val cultureCenterComplete: Boolean = false,
    val cultureCenterList: List<CommonCardModel> = emptyList(),
    val leisureSportsComplete: Boolean = false,
    val leisureSportsList: List<CommonCardModel> = emptyList(),
    val restaurantComplete: Boolean = false,
    val restaurantList: List<Restaurant> = emptyList(),
)

@Immutable
data class HomePagerItem(
    val image: String = "",
    val title: String = "",
    val address: String = "",
)