package kr.ksw.visitkorea.presentation.detail.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kr.ksw.visitkorea.domain.common.TYPE_RESTAURANT
import kr.ksw.visitkorea.domain.usecase.detail.GetDetailCommonUseCase
import kr.ksw.visitkorea.domain.usecase.detail.GetDetailImageUseCase
import kr.ksw.visitkorea.domain.usecase.detail.GetDetailIntroUseCase
import kr.ksw.visitkorea.domain.usecase.util.toImageUrl
import kr.ksw.visitkorea.presentation.common.DetailParcel
import kr.ksw.visitkorea.presentation.core.getResult
import kr.ksw.visitkorea.presentation.core.viewModelLauncher
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
                contentTypeId = detailParcel.contentTypeId,
                eventStartDate = detailParcel.eventStartDate,
                eventEndDate = detailParcel.eventEndDate
            )
        }
        getDetailCommon(detailParcel.contentId)
        getDetailIntro(
            detailParcel.contentId,
            detailParcel.contentTypeId
        )
        if(detailParcel.contentTypeId == TYPE_RESTAURANT) {
            getDetailMenuImage(detailParcel.contentId)
        } else {
            getDetailImage(detailParcel.contentId)
        }
    }

    private fun getDetailCommon(contentId: String) {
        viewModelLauncher {
            getDetailCommonUseCase(contentId)
                .getResult { result ->
                    _detailState.update {
                        it.copy(
                            detailCommon = result
                        )
                    }
                }
        }
    }

    private fun getDetailIntro(
        contentId: String,
        contentTypeId: String
    ) {
        viewModelLauncher {
            getDetailIntroUseCase(
                contentId,
                contentTypeId
            ).getResult { result ->
                _detailState.update {
                    it.copy(
                        detailIntro = result
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
            ).getResult { result ->
                _detailState.update {
                    it.copy(
                        images = result.map { image ->
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

    private fun getDetailMenuImage(
        contentId: String
    ) {
        viewModelLauncher {
            val result = getDetailImageUseCase(
                contentId, "N"
            ).getOrNull()
            if(result.isNullOrEmpty()) {
                getDetailImage(contentId)
            } else {
                _detailState.update {
                    it.copy(
                        images = result.map { image ->
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