package kr.ksw.visitkorea.presentation.hotel.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kr.ksw.visitkorea.domain.usecase.hotel.GetHotelListUseCase
import kr.ksw.visitkorea.domain.usecase.mapper.toCommonCardModel
import kr.ksw.visitkorea.presentation.core.BaseViewModel
import kr.ksw.visitkorea.presentation.core.getResult
import kr.ksw.visitkorea.presentation.core.viewModelLauncher
import javax.inject.Inject

@SuppressLint("MissingPermission")
@HiltViewModel
class HotelViewModel @Inject constructor(
    private val getHotelListUseCase: GetHotelListUseCase,
    fusedLocationProviderClient: FusedLocationProviderClient
): BaseViewModel<HotelUiEffect>(fusedLocationProviderClient) {
    private val _hotelState = MutableStateFlow(HotelState())
    val hotelState: StateFlow<HotelState>
        get() = _hotelState.asStateFlow()

    init {
        getWithLocation { lat, lng ->
            getHotelList(
                lat = lat,
                lng = lng,
            )
        }
    }

    fun onAction(action: HotelActions) {
        when(action) {
            is HotelActions.ClickCardItem -> {
                postUIEffect(HotelUiEffect.StartDetailActivity(action.data))
            }
        }
    }

    private fun getHotelList(
        lat: String,
        lng: String,
    ) {
        viewModelLauncher {
            getHotelListUseCase(
                false,
                lng,
                lat
            ).getResult { result ->
                _hotelState.update { state ->
                    state.copy(
                        isLoading = false,
                        hotelCardModelFlow = result.map { pagingData ->
                            pagingData.map {
                                it.toCommonCardModel()
                            }
                        }.cachedIn(viewModelScope)
                    )
                }
            }
        }
    }
}