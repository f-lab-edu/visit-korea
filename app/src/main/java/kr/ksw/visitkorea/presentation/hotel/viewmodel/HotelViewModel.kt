package kr.ksw.visitkorea.presentation.hotel.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kr.ksw.visitkorea.domain.usecase.hotel.GetHotelListUseCase
import kr.ksw.visitkorea.domain.usecase.mapper.toCommonCardModel
import kr.ksw.visitkorea.presentation.common.latitudeToStringOrDefault
import kr.ksw.visitkorea.presentation.common.longitudeToStringOrDefault
import kr.ksw.visitkorea.presentation.core.BaseViewModel
import javax.inject.Inject

@SuppressLint("MissingPermission")
@HiltViewModel
class HotelViewModel @Inject constructor(
    private val getHotelListUseCase: GetHotelListUseCase,
    private val fusedLocationProviderClient: FusedLocationProviderClient
): BaseViewModel<HotelUiEffect>() {
    private val _hotelState = MutableStateFlow(HotelState())
    val hotelState: StateFlow<HotelState>
        get() = _hotelState.asStateFlow()

    init {
        fusedLocationProviderClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            CancellationTokenSource().token
        ).addOnCompleteListener { task ->
            getHotelList(
                lat = task.result.latitudeToStringOrDefault(),
                lng = task.result.longitudeToStringOrDefault(),
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
        viewModelScope.launch {
            val hotelListFlow = getHotelListUseCase(
                false,
                lng,
                lat
            ).getOrNull()?.map { pagingData ->
                pagingData.map {
                    it.toCommonCardModel()
                }
            }?.cachedIn(viewModelScope) ?: emptyFlow()

            delay(500)
            _hotelState.update {
                it.copy(
                    isLoading = false,
                    hotelCardModelFlow = hotelListFlow
                )
            }
        }
    }
}