package kr.ksw.visitkorea.presentation.hotel.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.ksw.visitkorea.domain.usecase.hotel.GetHotelListUseCase
import javax.inject.Inject

@HiltViewModel
class HotelViewModel @Inject constructor(
    private val getHotelListUseCase: GetHotelListUseCase
): ViewModel() {

}