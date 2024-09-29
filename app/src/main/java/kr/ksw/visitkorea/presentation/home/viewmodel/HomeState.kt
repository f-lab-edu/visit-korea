package kr.ksw.visitkorea.presentation.home.viewmodel

import androidx.compose.runtime.Immutable
import kr.ksw.visitkorea.domain.usecase.model.TouristSpot

@Immutable
data class HomeState(
    val mainImage: String = "",
    val touristSpoList: List<TouristSpot> = emptyList()
)