package kr.ksw.visitkorea.presentation.home.viewmodel

import androidx.compose.runtime.Immutable
import kr.ksw.visitkorea.domain.usecase.model.CultureCenter
import kr.ksw.visitkorea.domain.usecase.model.LeisureSports
import kr.ksw.visitkorea.domain.usecase.model.Restaurant
import kr.ksw.visitkorea.domain.usecase.model.TouristSpot

@Immutable
data class HomeState(
    val mainImage: String = "",
    val touristSpotList: List<TouristSpot> = emptyList(),
    val cultureCenterList: List<CultureCenter> = emptyList(),
    val leisureSportsList: List<LeisureSports> = emptyList(),
    val restaurantList: List<Restaurant> = emptyList()
)