package kr.ksw.visitkorea.presentation.home.viewmodel

import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kr.ksw.visitkorea.domain.usecase.home.GetCultureCenterForHomeUseCase
import kr.ksw.visitkorea.domain.usecase.home.GetLeisureSportsForHomeUseCase
import kr.ksw.visitkorea.domain.usecase.home.GetRestaurantForHomeUseCase
import kr.ksw.visitkorea.domain.usecase.home.GetTouristSpotForHomeUseCase
import kr.ksw.visitkorea.presentation.core.BaseViewModel
import kr.ksw.visitkorea.presentation.core.getResult
import kr.ksw.visitkorea.presentation.core.viewModelLauncher
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTouristSpotForHomeUseCase: GetTouristSpotForHomeUseCase,
    private val getCultureCenterForHomeUseCase: GetCultureCenterForHomeUseCase,
    private val getLeisureSportsForHomeUseCase: GetLeisureSportsForHomeUseCase,
    private val getRestaurantForHomeUseCase: GetRestaurantForHomeUseCase,
    fusedLocationProviderClient: FusedLocationProviderClient
): BaseViewModel<HomeUiEffect>(fusedLocationProviderClient) {
    private val _homeState = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState>
        get() = _homeState.asStateFlow()

    init {
        getWithLocation { lat, lng ->
            getTouristSpot(lat, lng)
            getCultureCenter(lat, lng)
            getLeisureSports(lat, lng)
            getRestaurant(lat, lng)
        }
    }

    fun onAction(action: HomeActions) {
        when(action) {
            is HomeActions.ClickMoreButton -> {
                postUIEffect(
                    HomeUiEffect.StartMoreActivity(action.contentType)
                )
            }
            is HomeActions.ClickCardItem -> {
                postUIEffect(
                    HomeUiEffect.StartDetailActivity(action.data)
                )
            }
        }
    }

    private fun getTouristSpot(
        lat: String,
        lng: String,
    ) {
        viewModelLauncher {
            getTouristSpotForHomeUseCase(
                mapX = lng,
                mapY = lat
            ).getResult { result ->
                _homeState.update {
                    it.copy(
                        mainImage = result[0].firstImage,
                        touristSpotList = result
                    )
                }
            }
        }
    }

    private fun getCultureCenter(
        lat: String,
        lng: String,
    ) {
        viewModelLauncher {
            getCultureCenterForHomeUseCase(
                mapX = lng,
                mapY = lat
            ).getResult { result ->
                _homeState.update {
                    it.copy(
                        cultureCenterList = result
                    )
                }
            }
        }
    }

    private fun getRestaurant(
        lat: String,
        lng: String,
    ) {
        viewModelLauncher {
            getRestaurantForHomeUseCase(
                mapX = lng,
                mapY = lat
            ).getResult { result ->
                _homeState.update {
                    it.copy(
                        restaurantList = result
                    )
                }
            }
        }
    }

    private fun getLeisureSports(
        lat: String,
        lng: String,
    ) {
        viewModelLauncher {
            getLeisureSportsForHomeUseCase(
                mapX = lng,
                mapY = lat
            ).getResult { result ->
                _homeState.update {
                    it.copy(
                        leisureSportsList = result
                    )
                }
            }
        }
    }
}