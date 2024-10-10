package kr.ksw.visitkorea.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kr.ksw.visitkorea.domain.usecase.home.GetCultureCenterForHomeUseCase
import kr.ksw.visitkorea.domain.usecase.home.GetLeisureSportsForHomeUseCase
import kr.ksw.visitkorea.domain.usecase.home.GetRestaurantForHomeUseCase
import kr.ksw.visitkorea.domain.usecase.home.GetTouristSpotForHomeUseCase
import kr.ksw.visitkorea.presentation.common.ContentType
import kr.ksw.visitkorea.presentation.common.DetailParcel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTouristSpotForHomeUseCase: GetTouristSpotForHomeUseCase,
    private val getCultureCenterForHomeUseCase: GetCultureCenterForHomeUseCase,
    private val getLeisureSportsForHomeUseCase: GetLeisureSportsForHomeUseCase,
    private val getRestaurantForHomeUseCase: GetRestaurantForHomeUseCase
): ViewModel() {
    private val _homeState = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState>
        get() = _homeState.asStateFlow()

    private val _homeUiEffect = MutableSharedFlow<HomeUiEffect>(replay = 0)
    val homeUiEffect: SharedFlow<HomeUiEffect>
        get() = _homeUiEffect.asSharedFlow()

    init {
        getTouristSpot()
        getCultureCenter()
        getLeisureSports()
        getRestaurant()
    }

    fun onAction(action: HomeActions) {
        when(action) {
            is HomeActions.ClickMoreButton -> {
                startMoreActivity(action.contentType)
            }
            is HomeActions.ClickCardItem -> {
                startDetailActivity(action.data)
            }
        }
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
                        touristSpotList = items
                    )
                }
            }
        }
    }

    private fun getCultureCenter() {
        viewModelScope.launch {
            val items = getCultureCenterForHomeUseCase(
                "126.9817290217",
                "37.5678958128"
            ).getOrNull()
            if(items != null) {
                _homeState.update {
                    it.copy(
                        cultureCenterList = items
                    )
                }
            }
        }
    }

    private fun getRestaurant() {
        viewModelScope.launch {
            val items = getRestaurantForHomeUseCase(
                "126.9817290217",
                "37.5678958128"
            ).getOrNull()
            if(items != null) {
                _homeState.update {
                    it.copy(
                        restaurantList = items
                    )
                }
            }
        }
    }

    private fun getLeisureSports() {
        viewModelScope.launch {
            val items = getLeisureSportsForHomeUseCase(
                "126.9817290217",
                "37.5678958128"
            ).getOrNull()
            if(items != null) {
                _homeState.update {
                    it.copy(
                        leisureSportsList = items
                    )
                }
            }
        }
    }

    private fun startMoreActivity(contentType: ContentType) {
        viewModelScope.launch {
            _homeUiEffect.emit(HomeUiEffect.StartMoreActivity(contentType))
        }
    }

    private fun startDetailActivity(data: DetailParcel) {
        viewModelScope.launch {
            _homeUiEffect.emit(HomeUiEffect.StartDetailActivity(data))
        }
    }
}