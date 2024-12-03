package kr.ksw.visitkorea.presentation.home.viewmodel

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kr.ksw.visitkorea.domain.model.CardData
import kr.ksw.visitkorea.domain.usecase.home.GetCultureCenterForHomeUseCase
import kr.ksw.visitkorea.domain.usecase.home.GetLeisureSportsForHomeUseCase
import kr.ksw.visitkorea.domain.usecase.home.GetRestaurantForHomeUseCase
import kr.ksw.visitkorea.domain.usecase.home.GetTouristSpotForHomeUseCase
import kr.ksw.visitkorea.presentation.core.BaseViewModel
import kr.ksw.visitkorea.presentation.core.viewModelLauncher
import kr.ksw.visitkorea.presentation.home.mapper.toHomePagerItem
import javax.inject.Inject

@SuppressLint("MissingPermission")
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
            val items = getTouristSpotForHomeUseCase(
                lng,
                lat
            ).getOrNull()?.getShuffledItem() ?: emptyList()
            delay(500)
            _homeState.update {
                it.copy(
                    touristSpotList = items,
                    touristSpotComplete = true
                )
            }
            setMainPagerData()
        }
    }

    private fun getCultureCenter(
        lat: String,
        lng: String,
    ) {
        viewModelLauncher {
            val items = getCultureCenterForHomeUseCase(
                lng,
                lat
            ).getOrNull()?.getShuffledItem() ?: emptyList()
            delay(500)
            _homeState.update {
                it.copy(
                    cultureCenterList = items,
                    cultureCenterComplete = true
                )
            }
            setMainPagerData()
        }
    }

    private fun getLeisureSports(
        lat: String,
        lng: String,
    ) {
        viewModelLauncher {
            val items = getLeisureSportsForHomeUseCase(
                lng,
                lat
            ).getOrNull()?.getShuffledItem() ?: emptyList()
            delay(500)
            _homeState.update {
                it.copy(
                    leisureSportsList = items,
                    leisureSportsComplete = true
                )
            }
            setMainPagerData()
        }
    }

    private fun getRestaurant(
        lat: String,
        lng: String,
    ) {
        viewModelLauncher {
            val items = getRestaurantForHomeUseCase(
                lng,
                lat
            ).getOrNull()?.getShuffledItem() ?: emptyList()
            delay(500)
            _homeState.update {
                it.copy(
                    restaurantList = items,
                    restaurantComplete = true
                )
            }
            setMainPagerData()
        }
    }

    private fun <T> List<T>.getShuffledItem(): List<T> {
        if(size <= 10)
            return this
        return shuffled().take(10)
    }

    private fun setMainPagerData() {
        val state = _homeState.value
        if(state.touristSpotComplete &&
            state.cultureCenterComplete &&
            state.leisureSportsComplete &&
            state.restaurantComplete) {
            val homePagerItems: MutableList<HomePagerItem> = mutableListOf()
            homePagerItems.addHomePagerItem(state.touristSpotList)
            homePagerItems.addHomePagerItem(state.cultureCenterList)
            homePagerItems.addHomePagerItem(state.leisureSportsList)
            homePagerItems.addHomePagerItem(state.restaurantList)
            _homeState.update {
                it.copy(
                    mainPagerItems = homePagerItems.toList()
                )
            }
        }
    }

    private fun MutableList<HomePagerItem>.addHomePagerItem(
        data: List<CardData>
    ) {
        data.randomOrNull()?.run {
            add(this.toHomePagerItem())
        }
    }
}