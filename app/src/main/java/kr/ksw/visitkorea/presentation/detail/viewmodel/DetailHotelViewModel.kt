package kr.ksw.visitkorea.presentation.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kr.ksw.visitkorea.domain.usecase.detail.GetDetailImageUseCase
import kr.ksw.visitkorea.domain.usecase.detail.GetHotelDetailUseCase
import kr.ksw.visitkorea.domain.usecase.detail.GetHotelRoomDetailUseCase
import kr.ksw.visitkorea.domain.usecase.util.toImageUrl
import kr.ksw.visitkorea.presentation.common.DetailParcel
import kr.ksw.visitkorea.presentation.core.viewModelLauncher
import javax.inject.Inject

@HiltViewModel
class DetailHotelViewModel @Inject constructor(
    private val getHotelDetailUseCase: GetHotelDetailUseCase,
    private val getHotelRoomDetailUseCase: GetHotelRoomDetailUseCase,
    private val getDetailImageUseCase: GetDetailImageUseCase,
): ViewModel() {
    private val _hotelDetailState = MutableStateFlow(DetailHotelState())
    val hotelDetailState: StateFlow<DetailHotelState>
        get() = _hotelDetailState.asStateFlow()

    fun onAction(action: DetailHotelActions) {
        when(action) {
            DetailHotelActions.OnClickFacilityInfoButton -> {
                showFacilityInfoState()
            }
            DetailHotelActions.OnClickRoomInfoButton -> {
                showRoomInfoState()
            }
        }
    }

    fun initDetail(
        detailParcel: DetailParcel
    ) {
        _hotelDetailState.update {
            it.copy(
                title = detailParcel.title,
                firstImage = detailParcel.firstImage,
                address = detailParcel.address,
                dist = detailParcel.dist,
            )
        }
        getHotelDetail(detailParcel.contentId)
        getHotelRoomDetail(detailParcel.contentId)
        getDetailImage(detailParcel.contentId)
    }

    private fun getHotelDetail(contentId: String) {
        viewModelLauncher {
            getHotelDetailUseCase(contentId)
                .getOrNull()?.run {
                    _hotelDetailState.update {
                        it.copy(
                            hotelDetail = this
                        )
                    }
                }
        }
    }

    private fun getHotelRoomDetail(contentId: String) {
        viewModelLauncher {
            getHotelRoomDetailUseCase(contentId)
                .getOrNull()?.run {
                    _hotelDetailState.update {
                        it.copy(
                            hotelRoomDetail = this
                        )
                    }
                }
        }
    }

    private fun getDetailImage(
        contentId: String
    ) {
        viewModelLauncher {
            getDetailImageUseCase(
                contentId, "Y"
            ).getOrNull()?.run {
                _hotelDetailState.update {
                    it.copy(
                        images = this.map { image ->
                            image.copy(
                                originImgUrl = image.originImgUrl.toImageUrl(),
                                smallImageUrl = image.smallImageUrl.toImageUrl()
                            )
                        }
                    )
                }
            }
        }
    }

    private fun showFacilityInfoState() {
        _hotelDetailState.update {
            it.copy(
                showFacilityInfo = true,
                showRoomDetail = false
            )
        }
    }

    private fun showRoomInfoState() {
        _hotelDetailState.update {
            it.copy(
                showFacilityInfo = false,
                showRoomDetail = true
            )
        }
    }
}