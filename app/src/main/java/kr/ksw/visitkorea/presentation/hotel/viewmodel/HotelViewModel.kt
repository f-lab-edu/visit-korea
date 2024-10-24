package kr.ksw.visitkorea.presentation.hotel.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kr.ksw.visitkorea.domain.usecase.hotel.GetHotelListUseCase
import kr.ksw.visitkorea.domain.usecase.mapper.toCommonCardModel
import kr.ksw.visitkorea.presentation.common.DEFAULT_LATITUDE
import kr.ksw.visitkorea.presentation.common.DEFAULT_LONGITUDE
import kr.ksw.visitkorea.presentation.common.DetailParcel
import kr.ksw.visitkorea.presentation.common.latitudeToStringOrDefault
import kr.ksw.visitkorea.presentation.common.longitudeToStringOrDefault
import kr.ksw.visitkorea.presentation.more.viewmodel.MoreUiEffect
import javax.inject.Inject

@SuppressLint("MissingPermission")
@HiltViewModel
class HotelViewModel @Inject constructor(
    private val getHotelListUseCase: GetHotelListUseCase,
    private val fusedLocationProviderClient: FusedLocationProviderClient
): ViewModel() {
    private val _hotelState = MutableStateFlow(HotelState())
    val hotelState: StateFlow<HotelState>
        get() = _hotelState.asStateFlow()

    private val _hotelUiEffect = MutableSharedFlow<HotelUiEffect>(replay = 0)
    val hotelUiEffect: SharedFlow<HotelUiEffect>
        get() = _hotelUiEffect.asSharedFlow()

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
            getHotelList(
                lat = result.first,
                lng = result.second,
            )
        }
    }

    fun onAction(action: HotelActions) {
        when(action) {
            is HotelActions.ClickCardItem -> {
                startDetailActivity(action.data)
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

    private fun startDetailActivity(data: DetailParcel) {
        viewModelScope.launch {
            _hotelUiEffect.emit(HotelUiEffect.StartDetailActivity(data))
        }
    }
}