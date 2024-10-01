package kr.ksw.visitkorea.presentation.hotel.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kr.ksw.visitkorea.domain.usecase.hotel.GetHotelListUseCase
import kr.ksw.visitkorea.domain.usecase.mapper.toCommonCardModel
import javax.inject.Inject

@HiltViewModel
class HotelViewModel @Inject constructor(
    private val getHotelListUseCase: GetHotelListUseCase
): ViewModel() {
    private val _hotelState = MutableStateFlow(HotelState())
    val hotelState: StateFlow<HotelState>
        get() = _hotelState.asStateFlow()

    init {
        getHotelList()
    }

    private fun getHotelList(forceFetch: Boolean = false) {
        viewModelScope.launch {
            val hotelListFlow = getHotelListUseCase(
                forceFetch,
                "126.9817290217",
                "37.5678958128"
            ).getOrNull()
            if(hotelListFlow != null) {
                val hotelCardModelFlow = hotelListFlow.map { pagingData ->
                    pagingData.map {
                        it.toCommonCardModel()
                    }
                }.cachedIn(viewModelScope)

                _hotelState.update {
                    it.copy(
                        hotelCardModelFlow = hotelCardModelFlow
                    )
                }
            }
        }
    }
}