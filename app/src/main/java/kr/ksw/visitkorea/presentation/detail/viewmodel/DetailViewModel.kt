package kr.ksw.visitkorea.presentation.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kr.ksw.visitkorea.domain.common.TYPE_RESTAURANT
import kr.ksw.visitkorea.domain.usecase.detail.GetDetailCommonUseCase
import kr.ksw.visitkorea.domain.usecase.detail.GetDetailImageUseCase
import kr.ksw.visitkorea.domain.usecase.detail.GetDetailIntroUseCase
import kr.ksw.visitkorea.domain.usecase.util.toImageUrl
import kr.ksw.visitkorea.presentation.common.DetailParcel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getDetailCommonUseCase: GetDetailCommonUseCase,
    private val getDetailIntroUseCase: GetDetailIntroUseCase,
    private val getDetailImageUseCase: GetDetailImageUseCase
): ViewModel() {
    private val _detailState = MutableStateFlow(DetailState())
    val detailState: StateFlow<DetailState>
        get() = _detailState.asStateFlow()

    fun initDetail(
        detailParcel: DetailParcel
    ) {
        _detailState.update {
            it.copy(
                title = detailParcel.title,
                firstImage = detailParcel.firstImage,
                address = detailParcel.address,
                dist = detailParcel.dist,
                contentTypeId = detailParcel.contentTypeId
            )
        }
        getDetailCommon(detailParcel.contentId)
        getDetailIntro(
            detailParcel.contentId,
            detailParcel.contentTypeId
        )
        getDetailImage(
            detailParcel.contentId,
            if(detailParcel.contentTypeId == TYPE_RESTAURANT)
                "N"
            else
                "Y"
        )
    }

    private fun getDetailCommon(contentId: String) {
        viewModelScope.launch {
            getDetailCommonUseCase(contentId).getOrNull()?.run {
                _detailState.update {
                    it.copy(
                        detailCommon = this
                    )
                }
            }
        }
    }

    private fun getDetailIntro(
        contentId: String,
        contentTypeId: String
    ) {
        viewModelScope.launch {
            getDetailIntroUseCase(
                contentId,
                contentTypeId
            ).getOrNull()?.run {
                _detailState.update {
                    it.copy(
                        detailIntro = this
                    )
                }
            }
        }
    }

    private fun getDetailImage(
        contentId: String,
        imageYN: String
    ) {
        viewModelScope.launch {
            getDetailImageUseCase(
                contentId, imageYN
            ).getOrNull()?.run {
                _detailState.update {
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
}