package kr.ksw.visitkorea.presentation.hotel.viewmodel

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kr.ksw.visitkorea.domain.model.CommonCardModel

@Immutable
data class HotelState(
    val isLoading: Boolean = true,
    val hotelCardModelFlow: Flow<PagingData<CommonCardModel>> = emptyFlow(),
)