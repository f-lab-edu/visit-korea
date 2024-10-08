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
        contentId: String,
        contentTypeId: String
    ) {
        getDetailCommon(contentId)
        getDetailIntro(contentId, contentTypeId)
        getDetailImage(contentId, if(contentTypeId == TYPE_RESTAURANT) "N" else "Y")
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
                        images = this
                    )
                }
            }
        }
    }
}