package kr.ksw.visitkorea.presentation.detail.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kr.ksw.visitkorea.domain.usecase.detail.GetDetailCommonUseCase
import kr.ksw.visitkorea.domain.usecase.detail.GetDetailImageUseCase
import kr.ksw.visitkorea.domain.usecase.detail.GetHotelDetailUseCase
import kr.ksw.visitkorea.domain.usecase.detail.GetHotelRoomDetailUseCase
import kr.ksw.visitkorea.domain.usecase.util.toImageUrl
import kr.ksw.visitkorea.presentation.common.DetailParcel
import javax.inject.Inject

@HiltViewModel
class DetailHotelViewModel @Inject constructor(
    private val getDetailCommonUseCase: GetDetailCommonUseCase,
    private val getHotelDetailUseCase: GetHotelDetailUseCase,
    private val getHotelRoomDetailUseCase: GetHotelRoomDetailUseCase,
    private val getDetailImageUseCase: GetDetailImageUseCase,
): ViewModel() {
    private val _hotelDetailState = MutableStateFlow(DetailHotelState())
    val hotelDetailState: StateFlow<DetailHotelState>
        get() = _hotelDetailState.asStateFlow()

    private val _hotelDetailUIEffect = MutableSharedFlow<DetailHotelUIEffect>(replay = 0)
    val hotelDetailUIEffect: SharedFlow<DetailHotelUIEffect>
        get() = _hotelDetailUIEffect.asSharedFlow()

    fun onAction(action: DetailHotelActions) {
        when(action) {
            DetailHotelActions.OnClickFacilityInfoButton -> {
                showFacilityInfoState()
            }
            DetailHotelActions.OnClickRoomInfoButton -> {
                showRoomInfoState()
            }
            is DetailHotelActions.ClickDetailImages -> {
                openImageViewPager(
                    selectedImage = action.selectedImage,
                    images = _hotelDetailState.value.images.map { imageDTO ->
                        imageDTO.originImgUrl
                    }
                )
            }
            DetailHotelActions.ClickBackButtonWhenViewPagerOpened -> {
                _hotelDetailState.update {
                    it.copy(
                        viewPagerOpen = false
                    )
                }
            }
            is DetailHotelActions.ClickRoomDetailImages -> {
                openImageViewPager(
                    selectedImage = action.selectedImage,
                    images = _hotelDetailState.value.hotelRoomDetail[action.selectedRoomIndex].roomImages
                )
            }
            DetailHotelActions.ClickViewMapButton -> {
                openMapApp()
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
        getDetailCommon(detailParcel.contentId)
        getHotelDetail(detailParcel.contentId)
        getHotelRoomDetail(detailParcel.contentId)
        getDetailImage(detailParcel.contentId)
    }

    private fun getHotelDetail(contentId: String) {
        viewModelScope.launch {
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
        viewModelScope.launch {
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
        viewModelScope.launch {
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

    private fun getDetailCommon(contentId: String) {
        viewModelScope.launch {
            getDetailCommonUseCase(contentId)
                .getOrNull()?.run {
                    _hotelDetailState.update {
                        it.copy(
                            homePage = homepage,
                            lat = lat,
                            lng = lng
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

    private fun openImageViewPager(
        selectedImage: Int,
        images: List<String>
    ) {
        _hotelDetailState.update {
            it.copy(
                viewPagerOpen = true,
                selectedImage = selectedImage,
                viewPagerImages = images
            )
        }
    }

    private fun openMapApp() {
        viewModelScope.launch {
            val state = _hotelDetailState.value
            _hotelDetailUIEffect.emit(
                DetailHotelUIEffect.OpenMapApplication(
                    name = state.title,
                    lat = state.lat,
                    lng = state.lng
                )
            )
        }
    }
}