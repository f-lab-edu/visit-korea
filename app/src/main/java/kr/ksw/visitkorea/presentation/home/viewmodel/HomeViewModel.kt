package kr.ksw.visitkorea.presentation.home.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
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
import kr.ksw.visitkorea.presentation.common.DEFAULT_LATITUDE
import kr.ksw.visitkorea.presentation.common.DEFAULT_LONGITUDE
import kr.ksw.visitkorea.presentation.common.DetailParcel
import kr.ksw.visitkorea.presentation.common.latitudeToStringOrDefault
import kr.ksw.visitkorea.presentation.common.longitudeToStringOrDefault
import javax.inject.Inject

@SuppressLint("MissingPermission")
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTouristSpotForHomeUseCase: GetTouristSpotForHomeUseCase,
    private val getCultureCenterForHomeUseCase: GetCultureCenterForHomeUseCase,
    private val getLeisureSportsForHomeUseCase: GetLeisureSportsForHomeUseCase,
    private val getRestaurantForHomeUseCase: GetRestaurantForHomeUseCase,
    private val fusedLocationProviderClient: FusedLocationProviderClient
): ViewModel() {
    private val _homeState = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState>
        get() = _homeState.asStateFlow()

    private val _homeUiEffect = MutableSharedFlow<HomeUiEffect>(replay = 0)
    val homeUiEffect: SharedFlow<HomeUiEffect>
        get() = _homeUiEffect.asSharedFlow()

    init {
        fusedLocationProviderClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            CancellationTokenSource().token
        ).addOnCompleteListener { task ->
            val result = try {
                task.result.latitudeToStringOrDefault() to
                        task.result.longitudeToStringOrDefault()
            } catch (e: Exception) {
                DEFAULT_LATITUDE to DEFAULT_LONGITUDE
            }
            result.run {
                getTouristSpot(first, second)
                getCultureCenter(first, second)
                getLeisureSports(first, second)
                getRestaurant(first, second)
            }
        }
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

    private fun getTouristSpot(
        lat: String,
        lng: String,
    ) {
        viewModelScope.launch {
            val items = getTouristSpotForHomeUseCase(
                lng,
                lat
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

    private fun getCultureCenter(
        lat: String,
        lng: String,
    ) {
        viewModelScope.launch {
            val items = getCultureCenterForHomeUseCase(
                lng,
                lat
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

    private fun getRestaurant(
        lat: String,
        lng: String,
    ) {
        viewModelScope.launch {
            val items = getRestaurantForHomeUseCase(
                lng,
                lat
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

    private fun getLeisureSports(
        lat: String,
        lng: String,
    ) {
        viewModelScope.launch {
            val items = getLeisureSportsForHomeUseCase(
                lng,
                lat
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