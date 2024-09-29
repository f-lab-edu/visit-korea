package kr.ksw.visitkorea.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kr.ksw.visitkorea.domain.usecase.home.GetTouristSpotForHomeUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTouristSpotForHomeUseCase: GetTouristSpotForHomeUseCase
): ViewModel() {
    private val _homeState = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState>
        get() = _homeState.asStateFlow()

    init {
        getTouristSpot()
    }

    private fun getTouristSpot() {
        viewModelScope.launch {
            val items = getTouristSpotForHomeUseCase(
                "126.9817290217",
                "37.5678958128"
            ).getOrNull()
            if(items != null) {
                _homeState.update {
                    it.copy(
                        mainImage = items[0].firstImage,
                        touristSpoList = items
                    )
                }
            }
        }
    }
}